import {
    showNotification, NotificationType,
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
                return axios.get("/api/v1/dx/userList/fordx", {})
                            .then(response => {
                                let response_ = response.data || []; // Get the data from the response
                                console.log('response', response_);
                                return response_;
                            })
                            .catch(error => {
                                console.error("Error loading entities: ", error);
                                let message = String.format("Error loading entities: %s", error.toString());
                                showNotification(message, NotificationType.ERROR);
                                return [];
                            });
            },
            insert: function(values) {
                console.log('inserting....', values);
                return axios.post("/api/v1/dx/userList/fordx", values, {
                          withCredentials: true // Send cookies when calling the API
                     })
                    .then(response => {
                        let response_ = response.data;
                        console.log('response', response_);
                        showNotification("Entity inserted successfully.", NotificationType.SUCCESS);
                        return response_; // Return the inserted entity
                    })
                    .catch(error => {
                        console.error("Error inserting entity: ", error);
                        let message = "Error inserting entity: " + error;
                        showNotification(message, NotificationType.ERROR);
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

                return axios.put("/api/v1/dx/userList/fordx/" + key, updatedData, {
                   withCredentials: true // Send cookies when calling the API
                }).then(response => {
                    let response_ = response.data;
                    console.log('response', response_);
                    showNotification("Entity updated successfully.", NotificationType.SUCCESS);
                }).catch(error => {
                    console.error("Error updating entity: ", error);
                    let message = "Error updating entity: " + error;
                    showNotification(message, NotificationType.ERROR);
                    return Promise.reject(message);
                });
            },
            remove: function(key) {
                return axios.delete("/api/v1/dx/userList/fordx/" + key, {
                        withCredentials: true // Send cookies when calling the API
                    })
                    .then(response => {
                        let response_ = response.data;
                        console.log('response', response_);
                        showNotification("Entity deleted successfully.", NotificationType.SUCCESS);
                    })
                    .catch(error => {
                        console.error("Error deleting entity: ", error);
                        let message = "Error deleting entity: " + error;
                        showNotification(message, NotificationType.ERROR);
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
                { dataField: "name", validationRules: [{ type: "required" }] },
                { dataField: "value" },
                { dataField: "address", validationRules: [{ type: "required" }] },
                { dataField: "phoneNumber",
                    caption: "전화번호",
                    hint: "Enter a phone number in the format xxxxxx-xxxxxxx",
                    validationRules: [{ type: "required" }],
                    format: formatPhoneNumber,
                },
                { dataField: "ssn",
                    caption: "주민번호",
                    hint: "Enter a 13-digit SSN",
                    validationRules: [{ type: "required" }],
                    format: formatSSN,
                },
                { dataField: "email",
                    caption: "Email",
                    hint: "Enter a valid email address",
                    validationRules: [{ type: "required" }, { type: "email" }],
                },
                {
                    dataField: "description",
                    caption: "설명",
                    visible: false,
                },
                {
                    type: "buttons",
                    width: 100,
                    buttons: ["edit", "delete"],
                    visible: (e) => {
                        // Hide buttons if there's no data
                        return e.row && e.row.data && Object.keys(e.row.data).length > 0;
                    }
                },
            ],
            editing: {
                mode: "popup",         // edit mode: "form", "popup", "batch"
                popup: { // Customize the popup layout and appearance when it comes to editing with a popup mode
                    title: "User Info",
                    showTitle: true,
                    width: 800,
                    height: 525,
                },
                form: { // Customize the form layout and appearance when it comes to editing with a form mode
                    colCount: 2,
                    items: [
                        {
                            dataField: "name",
                            label: { text: "이름" },
                            editorType: "dxTextBox",
                            // editorOptions: { width: 200 },
                            validationRules: [{ type: "required" }],
                        },
                        {
                            dataField: "value",
                            label: { text: "값" },
                            editorType: "dxNumberBox",
                            // editorOptions: { width: 200 },
                        },
                        {
                            dataField: "address",
                            label: { text: "주소" },
                            editorType: "dxTextBox",
                            // editorOptions: { width: 200 },
                            validationRules: [{ type: "required" }],
                        },
                        {
                            dataField: "phoneNumber",
                            label: { text: "전화번호" },
                            editorType: "dxTextBox",
                            editorOptions: {
                                mask: "(000)-0000-0000",
                                maskChar: "_",
                                showMaskMode: "onFocus"
                            },
                            validationRules: [{ type: "required" }],
                        },
                        {
                            dataField: "ssn",
                            label: { text: "주민번호" },
                            editorType: "dxTextBox",
                            editorOptions: {
                                mask: "000000-0000000",
                                maskChar: "_",
                                showMaskMode: "onFocus"
                            },
                            validationRules: [{ type: "required" }]
                        },
                        {
                            dataField: "email",
                            label: { text: "이메일" },
                            editorType: "dxTextBox",
                            // editorOptions: { width: 200 },
                            validationRules: [{ type: "required" }, { type: "email" }], // Email validation
                        },
                        {
                            dataField: "description",
                            label: { text: "설명" },
                            editorType: "dxTextArea",
                            editorOptions: {
                                height: 100
                            },
                            colSpan: 2,
                        },
                    ]
                },
                allowAdding: true,    // Enable adding
                allowUpdating: true,  // Enable updating
                allowDeleting: true,  // Enable deleting
                useIcons: true,       // Display icons instead of text for edit/delete buttons
                confirmDelete: false, // Disable the default confirmation dialog
                texts: {
                    confirmDeleteMessage: "삭제할 거니?",
                    saveRowChanges: "저장",
                    cancelRowChanges: "취소",
                    deleteRow: "삭제",
                    editRow: "수정",
                    addRow: "추가",
                }
            },
            paging: {
                pageSize: 5
            },
            pager: {
                showPageSizeSelector: true,
                allowedPageSizes: [5, 10, 20],
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
                fileName: "userList",
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
                // Set default values for new rows
                e.data = {
                    name: "New User",
                    value: 0,
                    address: "New Address",
                    phoneNumber: "00000000000",
                    ssn: "0000000000000",
                    email: ""
                };
            },
            onExporting(e) {
                // Customize the exported Excel file
                console.log('Exporting to Excel...onExporting() : ');
                e.cancel = true; // Prevent the default export action

                // Show a prompt dialog to get the file name
                let defaultFileName = 'userList_' + new Date().toISOString().slice(0,10); // e.g., userList_2023-10-14
                //let fileName = prompt("Enter a file name:", defaultFileName);
                showPromptDialog("Export to Excel", "Enter a file name:", defaultFileName)
                    .then((fileName) => {
                        if (fileName) {
                            // Remove invalid characters from file name
                            fileName = fileName.replace(/[/\\?%*:|"<>]/g, '-').trim();
                            if(fileName === '') {
                                // Show a message if the file name is empty
                                showNotification("File name is required.", NotificationType.WARNING);
                            } else {
                                // Proceed with exporting using the provided file name
                                exportDataGridToExcel(e.component, fileName);
                            }
                        } else {
                            // User canceled or provided an empty file name
                            showNotification("Export canceled.", NotificationType.INFO);
                        }
                    });
            },
            onToolbarPreparing: function(e) {
                e.toolbarOptions.items.unshift(
                    {
                        location: "before",
                        widget: "dxButton",
                        options: {
                            icon: "exportxlsx",
                            text: "Export to Excel",
                        },
                        // onClick: function() {
                        //     console.log('Export to Excel...');
                        //     e.component.exportToExcel(false);
                        // },
                        template: function() {
                            return $("<div>").addClass("toolbar-header").text("사용자 목록");
                        }
                    }
                );
            }, // Customize the toolbar
            onRowInserting: function(e) {
                // Ensuure the data object exists
                e.data = e.data || {};
                console.log('inserting....', e.data);
            },
            onEditCanceling: function(e) {
                // Display custom message when cancel button is clicked
                DevExpress.ui.notify("수정 취소 하셨어요.", "info", 2000);
            },
            onDeleteCanceling: function(e) {
                // Display custom message when cancel button is clicked
                DevExpress.ui.notify("삭제 취소 하셨어요.", "info", 2000);
            },
            onRowRemoving: function(e) {
                // Display a confirmation dialog before deletion
                e.cancel = new Promise((resolve) => {
                    let dialogResult = DevExpress.ui.dialog.confirm("삭제할 거니?", "삭제 확인");
                    dialogResult.done(function(confirm) {
                        if (confirm) {
                            console.log('Deleting....', e.data);
                            resolve(false);  // Allow the deletion to proceed
                            // The actual deletion will be handled by the CustomStore's remove function
                        } else {
                            resolve(true);  // Cancel the deletion
                            DevExpress.ui.notify("취소 되었습니다.", "info", 1000);
                        }
                    });
                });
            },
        }).dxDataGrid("instance"); // Get the grid instance
    }

});