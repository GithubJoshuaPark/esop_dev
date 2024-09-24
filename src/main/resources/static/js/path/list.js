import {
    showCustomNotification, NotificationType,
    formatSSN, formatPhoneNumber, exportDataGridToExcel, showPromptDialog
} from "../utils/utils.js";

$(document).ready(function() {
    console.log("list module...");
    
    loadBoards();

    function loadBoards() {
        console.log('Loading Users...');

        let dxDataSource = new DevExpress.data.CustomStore({
            key: "id",
            load: function() {
                return axios.get("/api/v1/path/list", {})
                            .then(response => {
                                let response_ = response.data || []; // Default to empty array
                                console.log('response', response_);
                                return response_;
                            })
                            .catch(error => {
                                console.error("Error loading entities: ", error);
                                let message = String.format("Error loading entities: %s",error);
                                showCustomNotification(message, NotificationType.ERROR);
                                return [];
                            });
            },
            insert: function(values) {
                console.log('inserting....', values);
                return axios.post("/api/v1/path/list", values, {
                          withCredentials: true // Send cookies when calling the API
                     })
                    .then(response => {
                        let response_ = response.data;
                        console.log('response', response_);
                        showCustomNotification("Entity inserted successfully.", NotificationType.SUCCESS);
                        return response_; // Return the inserted entity
                    })
                    .catch(error => {
                        console.error("Error inserting entity: ", error);
                        let message = "Error inserting entity: " + error;
                        showCustomNotification(message, NotificationType.ERROR);
                        return Promise.reject(message);
                    });
            },
            update: function(key, values) {
                let grid = $("#gridContainer").dxDataGrid("instance");
                let data = grid.getDataSource().items().find(item => item.id === key);

                let updatedData = Object.assign({}, data, values);

                if(updatedData.phoneNumber) {
                    updatedData.phoneNumber = updatedData.phoneNumber.replace(/\D/g, '');
                }
                if(updatedData.ssn) {
                    updatedData.ssn = updatedData.ssn.replace(/\D/g, '');
                }

                console.log('updating....', updatedData);

                return axios.put("/api/v1/path/list/" + key, updatedData, {
                   withCredentials: true // Send cookies when calling the API
                }).then(response => {
                    let response_ = response.data;
                    console.log('response', response_);
                    showCustomNotification("Entity updated successfully.", NotificationType.SUCCESS);
                }).catch(error => {
                    console.error("Error updating entity: ", error);
                    let message = "Error updating entity: " + error;
                    showCustomNotification(message, NotificationType.ERROR);
                    return Promise.reject(message);
                });
            },
            remove: function(key) {
                return axios.delete("/api/v1/path/list/" + key, {
                        withCredentials: true // Send cookies when calling the API
                    })
                    .then(response => {
                        let response_ = response.data;
                        console.log('response', response_);
                        showCustomNotification("Entity deleted successfully.", NotificationType.SUCCESS);
                        return response_; // Return the deleted entity
                    })
                    .catch(error => {
                        console.error("Error deleting entity: ", error);
                        let message = "Error deleting entity: " + error;
                        showCustomNotification(message, NotificationType.ERROR);
                        return Promise.reject(message);
                    });
            }
        });

        let gridInstance = $("#gridContainer").dxDataGrid({
            dataSource: dxDataSource,
            key: "id",
            method: "GET",
            columns: [
                { dataField: "id", allowEditing: false, width: 50 },
                { dataField: "path", validationRules: [{ type: "required" }] },
                { dataField: "comment" },
                {
                    type: "buttons",
                    width: 100,
                    buttons: ["edit", "delete"],
                },
            ],
            editing: {
                mode: "popup",         // edit mode: "form", "popup", "batch"
                popup: {
                    title: "Path List",
                    showTitle: true,
                    width: 800,
                    height: 400,
                    position: { my: "center", at: "center", of: window },
                    dragEnabled: true,
                    closeOnOutsideClick: false, // Prevent closing the popup when clicking outside
                    // toolbarItems: [
                    //     {
                    //         toolbar: "bottom",
                    //         location: "after",
                    //         widget: "dxButton",
                    //         options: {
                    //             text: "저장",
                    //             onClick: function(e) {
                    //                 console.log('Custom button clicked...', e);
                    //                 // insert or update
                    //                 gridInstance.saveEditData();
                    //             }
                    //         }
                    //     },
                    //     {
                    //         toolbar: "bottom",
                    //         location: "after",
                    //         widget: "dxButton",
                    //         options: {
                    //             text: "취소",
                    //             onClick: function(e) {
                    //                 console.log('Custom button clicked...', e);
                    //                 // cancel
                    //                 gridInstance.cancelEditData();
                    //             }
                    //         }
                    //     }
                    // ],
                },
                form: {
                    items: [
                        {
                            itemType: "group",
                            colCount: 1,
                            items: [
                                {
                                    dataField: "path",
                                    label: {text: "Path"},
                                    editorType: "dxTextBox",
                                    editorOptions: {
                                        placeholder: "Path",
                                        showClearButton: true,
                                        width: 400,
                                    },
                                    validationRules: [{ type: "required" }]
                                },
                                {
                                    dataField: "comment",
                                    label: {text: "Comment"},
                                    editorType: "dxTextBox",
                                    editorOptions: {
                                        placeholder: "Comment",
                                        showClearButton: true,
                                        width: 400,
                                    }
                                }
                            ]
                        }
                    ]
                },
                allowAdding: true,    // Enable adding   (+)
                allowUpdating: true,  // Enable updating (!)
                //allowDeleting: true,  // Enable deleting (trash) --> Use custom toolbar button instead
                useIcons: true,       // Display icons instead of text for edit/delete buttons
                //confirmDelete: false, // Disable the default confirmation dialog
                texts: {
                    confirmDeleteMessage: "삭제할 거니?",
                    saveRowChanges: "저장",
                    cancelRowChanges: "취소",
                    deleteRow: "삭제",
                    editRow: "수정",
                    addRow: "추가",
                }
            },
            selection: {
                mode: "multiple",
                showCheckBoxesMode: "always", // "onClick" | "onLongTap" | "always" | "none"
                allowSelectAll: true,
            },
            paging: {
                pageSize: 10
            },
            pager: {
                showPageSizeSelector: true,
                allowedPageSizes: [10, 20, 30],
                showInfo: true,
                visible: true
            },
            filterRow: {
                visible: true
            },
            searchPanel: {
                visible: true,
                width: 240,
                placeholder: "Search..."
            },
            noDataText: "데이터 없어요.",             // Custom message when there's no data
            export: {
                enabled: true,                     // Enable the export feature
                //fileName: "pathList",
                //formats: ["XLSX", "CSV", "PDF"], // Formats that can be exported
                allowExportSelectedData: true,     // Enable/Disable exporting selected rows
                texts: {
                    exportTo: "Export to...",
                    exportAll: "Export all data",
                    exportSelectedRows: "Export selected rows",
                    excelFilterEnabled: true,
                    ignoreExcelErrors: true
                }
            },
            onInitNewRow: function(e) {
                e.data = {
                    path: "",
                    comment: ""
                };
            },
            onExporting(e) {
                // Customize the exported Excel file
                console.log('Exporting to Excel...onExporting() : ');
                e.cancel = true; // Prevent the default export action

                // Show a prompt dialog to get the file name
                let defaultFileName = 'pathList_' + new Date().toISOString().slice(0,10); // e.g., userList_2023-10-14
                //let fileName = prompt("Enter a file name:", defaultFileName);
                showPromptDialog("Export to Excel", "Enter a file name:", defaultFileName)
                    .then((fileName) => {
                        if (fileName) {
                            // Remove invalid characters from file name
                            fileName = fileName.replace(/[/\\?%*:|"<>]/g, '-').trim();
                            if(fileName === '') {
                                // Show a message if the file name is empty
                                showCustomNotification("File name is required.", NotificationType.WARNING);
                            } else {
                                // Proceed with exporting using the provided file name
                                exportDataGridToExcel(e.component, fileName);
                            }
                        } else {
                            // User canceled or provided an empty file name
                            showCustomNotification("Export canceled.", NotificationType.INFO);
                        }
                    });
            },
            onRowInserting: function(e) {
                e.data = e.data || {}; // Default to empty object
                console.log('inserting....', e.data);
            },
            onEditCanceling: function(e) {
                // Display custom message when cancel button is clicked
                DevExpress.ui.notify("수정 취소 하셨어요.", "info", 2000);
            },
            // onRowDeleting: function(e) {
            //     // Display custom message when delete button is clicked
            //     DevExpress.ui.notify("삭제 하셨어요.", "info", 2000);
            // },
            // onDeleteCanceling: function(e) {
            //     // Display custom message when cancel button is clicked
            //     DevExpress.ui.notify("삭제 취소 하셨어요.", "info", 2000);
            // },
            // onRowRemoving: function(e){
            //     // Display a confirmation dialog before deletion
            //     e.cancel = new Promise((resolve) => {
            //         let dialogResult = DevExpress.ui.dialog.confirm("삭제할 거니?", "삭제 확인");
            //         dialogResult.done(function(confirm) {
            //             if (confirm) {
            //                 console.log('Deleting....', e.data);
            //                 resolve(false);  // Allow the deletion to proceed
            //                 // The actual deletion will be handled by the CustomStore's remove function
            //             } else {
            //                 resolve(true);  // Cancel the deletion
            //                 DevExpress.ui.notify("취소 되었습니다.", "info", 1000);
            //             }
            //         });
            //     });
            // },
            onToolbarPreparing: function(e) {
                e.toolbarOptions.items.unshift(
                    {
                                location: "before",
                                widget: "dxButton",
                                options: {
                                    icon: "trash",
                                    text: "Delete",
                                    elementAttr: {
                                        class: "red-trash-icon"
                                    },
                                    onClick: function() {
                                        // Get selected row keys (IDs)
                                        // Delete selected rows (same as fixed code above)
                                        let selectedRowKeys = gridInstance.getSelectedRowKeys();

                                        if (selectedRowKeys.length === 0) {
                                            showCustomNotification("No rows selected", NotificationType.WARNING);
                                            return;
                                        }

                                        DevExpress.ui.dialog.confirm("선택 한 행들 삭제 할 겨?", "삭제 확인")
                                            .done(function(confirm) {
                                                if (confirm) {
                                                    // Iterate over selected rows and delete each one using the data source's remove function
                                                    selectedRowKeys.forEach(function(key) {
                                                        dxDataSource.remove(key).then(() => {
                                                            gridInstance.refresh(); // Refresh grid after deletion
                                                        });
                                                    });
                                                    showCustomNotification("삭제 잘 되었어요.", NotificationType.SUCCESS);
                                                }
                                            });
                                    }
                                }
                            },
                );
            }, // Customize the toolbar
        }).dxDataGrid("instance"); // Get the grid instance
    }

});