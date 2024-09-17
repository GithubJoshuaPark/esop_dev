import {showCustomNotification, NotificationType ,
    formatSSN, formatPhoneNumber, exportDataGridToExcel, showPromptDialog } from "../utils/utils.js";

$(document).ready(function() {
    console.log("list module...");
    
    loadBoards();

    function loadBoards() {
        console.log('Loading Entities...');

        let dxDataSource = new DevExpress.data.CustomStore({
            key: "id",
            load: function() {
                return axios.get("/api/v1/dx/entityList/fordx", {})
                    .then(response => {
                        let response_ = response.data || []; // Get the data from the response
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
                return $.ajax({
                    url: "/api/v1/dx/entityList/fordx",
                    method: "POST",
                    xhrFields: {
                        withCredentials: true // Send cookies when calling the API
                    },
                    data: JSON.stringify(values),
                    contentType: "application/json",
                    dataType: "json"
                });
            },
            update: function(key, values) {
                // values are the updated values(only) for the row
                // key is the id of the row
                // The key is used to identify the row to update
                // The all column values are need to put the updated values for the row
                // So, we need to get the existing data for the row and merge it with the updated values
                let grid = $("#gridContainer").dxDataGrid("instance");
                let data = grid.getDataSource().items().find(item => item.id === key);

                // Merge existing data with updated values
                let updatedData = Object.assign({}, data, values);

                // Process values before sending
                if (updatedData.phoneNumber) {
                    // Remove non-numeric characters
                    updatedData.phoneNumber = updatedData.phoneNumber.replace(/\D/g, '');
                }
                if (updatedData.ssn) {
                    // Remove non-numeric characters
                    updatedData.ssn = updatedData.ssn.replace(/\D/g, '');
                }

                log.debug('updating....', updatedData);
                return $.ajax({
                    url: "/api/v1/dx/entityList/fordx/" + key,
                    method: "PUT",
                    xhrFields: {
                        withCredentials: true // Send cookies when calling the API
                    },
                    data: JSON.stringify(updatedData),
                    contentType: "application/json",
                    dataType: "json"
                }).fail(function(xhr, status, error) {
                    // Handle errors
                    if (xhr.status === 400) {
                        // Display validation errors
                        let errorResponse = xhr.responseJSON;
                        if (errorResponse && errorResponse.errors) {
                            let message = errorResponse.errors.join('\n');
                            ShowCustomNotification(message, NotificationType.INFO);
                        } else {
                            let message = "An error occurred while updating the record.";
                            ShowCustomNotification(message, NotificationType.ERROR);
                        }
                    }
                    else {
                        console.error("Error updating entity: ", error);
                        let message = "Error updating entity: " + error;
                        ShowCustomNotification(message, NotificationType.ERROR);
                    }
                });
            },
            remove: function(key) {
                return $.ajax({
                    url: "/api/v1/dx/entityList/fordx/" + key,
                    method: "DELETE",
                    xhrFields: {
                        withCredentials: true // Send cookies when calling the API
                    },
                });
            }
        });

        $("#gridContainer").dxDataGrid({
            dataSource: dxDataSource,
            method: "GET",
            columns: [
                { dataField: "id", allowEditing: false, width: 50 },
                { dataField: "name", caption: "이름", validationRules: [{ type: "required" }] },
                { dataField: "value", caption: "값" },
                { dataField: "address",
                    caption: "주소",
                    validationRules: [{ type: "required" }]
                },
                { dataField: "phoneNumber",
                  caption: "전화번호",
                    hint: "Enter a phone number in the format xxxxxx-xxxxxxx",
                  validationRules: [{ type: "required" }],
                  customizeText: function(cellInfo) {
                    let value = cellInfo.value;
                    if(!value) return "";
                    let digits = value.replace(/\D/g, ""); // Remove non-numeric characters
                    // Apply format xxx-xxxx-xxxx
                    return digits.replace(/(\d{3})(\d{3})(\d{4})/, "($1) $2-$3");
                  },
                  editorOptions: {
                    mask: "000-0000-0000",
                    maskChar: "_",
                    showMaskMode: "onFocus" // Use the mask only when the editor is focused
                  }
                },
                { dataField: "ssn",
                    caption: "SSN",
                    hint: "Enter a 13-digit SSN",
                  validationRules: [{ type: "required" }],
                    customizeText: function(cellInfo) {
                        let value = cellInfo.value;
                        if(!value) return "";
                        let digits = value.replace(/\D/g, ""); // Remove non-numeric characters
                        // Apply format xxxxxx-xxxxxxx
                        return digits.replace(/(\d{6})(\d{7})/, '$1-$2');
                    },
                  editorOptions: {
                    mask: "000000-0000000",
                    maskChar: "_",
                    showMaskMode: "onFocus" // Use the mask only when the editor is focused
                  }
                },
                { caption: "Action",
                    type: "buttons",
                    width: 100,
                    buttons: ["edit", "delete"]
                },
            ],
            editing: {
                mode: "popup",  // edit mode: "form", "popup", "batch"
                popup: {
                    title: "User Info",
                    showTitle: true,
                    width: 800,
                    height: 525,
                },
                form: {
                    colCount: 2,
                    items: [
                        {
                            dataField: "name",
                            label: { text: "이름" },
                            editorType: "dxTextBox",
                            editorOptions: { width: 200 },
                            validationRules: [{ type: "required" }]
                        },
                        {
                            dataField: "value",
                            label: { text: "값" },
                            editorType: "dxNumberBox",
                            editorOptions: { width: 200 },
                        },
                        {
                            dataField: "address",
                            label: { text: "주소" },
                            editorType: "dxTextBox",
                            editorOptions: { width: 200 },
                            validationRules: [{ type: "required" }]
                        },
                        {
                            dataField: "phoneNumber",
                            label: { text: "전화번호" },
                            editorType: "dxTextBox",
                            editorOptions: {
                                mask: "000-0000-0000",
                                maskChar: "_",
                                showMaskMode: "onFocus"
                            },
                            validationRules: [{ type: "required" }]
                        },
                        {
                            dataField: "ssn",
                            label: { text: "SSN" },
                            editorType: "dxTextBox",
                            editorOptions: {
                                mask: "000000-0000000",
                                maskChar: "_",
                                showMaskMode: "onFocus"
                            },
                            validationRules: [{ type: "required" }]
                        },
                    ]
                },
                allowAdding: true,
                allowUpdating: true,
                allowDeleting: true, // Enable the delete button
                useIcons: true, // Display icons instead of text for edit/delete buttons
                confirmDelete: false, // Disable the default confirmation dialog
                texts: {
                    confirmDeleteMessage: "삭제할 거니?",
                    saveRowChanges: "저장",
                    cancelRowChanges: "취소",
                    deleteRow: "삭제",
                    editRow: "수정",
                    addRow: "추가",
                },
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
            noDataText: "데이터 없어요.", // Custom message when there's no data
            export: {
                enabled: true,                     // Enable the export feature
                fileName: "entityList",
                //formats: ["XLSX", "CSV", "PDF"], // Formats that can be exported
                allowExportSelectedData: true,     // Enable/Disable exporting selected rows
                texts: {
                    exportTo: "Export to...",
                    exportAll: "Export All",
                    exportSelectedRows: "Export Selected Rows",
                    excelFilterEnabled: true
                }
            },
            onInitNewRow: function(e) {
                // Set default values for new rows
                e.data = {
                    name: "New User",
                    value: 0,
                    address: "New Address",
                    phoneNumber: "000-0000-0000",
                    ssn: "000000-0000000"
                };
            },
            onExporting(e) {
                // Customize the exported Excel file
                console.log('Exporting to Excel...onExporting() : ');
                // get a file name from the user through the file save dialog
                e.cancel = true; // Cancel the default export action

                // Show a prompt dialog to get the file name
                let defaultFileName = 'entityList_' + new Date().toISOString().slice(0,10); // e.g., userList_2023-10-14
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
            onToolbarPreparing: function(e) {
                e.toolbarOptions.items.unshift(
                    {
                        location: "before",
                        widget: "dxButton",
                        options: {
                            icon: "exportxlsx",
                            text: "Export to Excel",
                        },
                        template: function() {
                            return $("<div>").addClass("toolbar-header").text("사용자 목록");
                        }
                    }
                );
            }, // Customize the toolbar
            onRowInserting: function(e) {
                // Display a custom message when a new row is added
                e.data = e.data || {}; // Ensure data is not null
                console.log('inserting....', e.data);
            },
            // Add the onEditCanceling event handler
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
        });
    }

});