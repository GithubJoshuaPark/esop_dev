import {showCustomNotification, NotificationType} from "../utils/utils.js";

$(document).ready(function() {
    console.log("list module...");
    
    loadBoards();

    function loadBoards() {
        console.log('Loading Users...');

        let dxDataSource = new DevExpress.data.CustomStore({
            key: "id",
            load: function() {
                return $.getJSON("/api/v1/dx/userList/fordx");
            },
            insert: function(values) {
                return $.ajax({
                    url: "/api/v1/dx/userList/fordx",
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
                return $.ajax({
                    url: "/api/v1/dx/userList/fordx/" + key,
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
                        let message = "";
                        if (errorResponse && errorResponse.errors) {
                            message = errorResponse.errors.join('\n');
                        } else {
                            message = "An error occurred while updating the record.";
                        }
                        showCustomNotification(message, NotificationType.INFO);
                    }
                    else {
                        console.error("Error updating entity: ", error);
                        let message = "Error updating entity: " + error;
                        showCustomNotification(message, NotificationType.ERROR);
                    }
                });
            },
            remove: function(key) {
                return $.ajax({
                    url: "/api/v1/dx/userList/fordx/" + key,
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
                { dataField: "name", validationRules: [{ type: "required" }] },
                { dataField: "value" },
                { dataField: "address", validationRules: [{ type: "required" }] },
                { dataField: "phoneNumber",
                    caption: "Phone Number",
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
                { dataField: "email",
                    caption: "Email",
                    hint: "Enter a valid email address",
                    validationRules: [{ type: "required" }],
                    customizeText: function(cellInfo) {
                        let value = cellInfo.value;
                        if(!value) return "";
                        let email = value;
                        return email;
                    },
                },
                {
                    type: "buttons",
                    width: 100,
                    buttons: ["edit", "delete"]
                },
            ],
            editing: {
                mode: "form",
                allowAdding: true,
                allowUpdating: true,
                allowDeleting: true,
                useIcons: true, // Display icons instead of text for edit/delete buttons
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
            noDataText: "데이터 없어요.", // Custom message when there's no data
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
                e.cancel = true; // Prevent the default deletion action until confirmed

                var dialogResult = DevExpress.ui.dialog.confirm("삭제할 거니?", "삭제 확인");

                dialogResult.done(function(confirm) {
                    if (confirm) {

                        console.log('deleting....', e.component);

                        // Proceed with deletion
                        e.component.deleteRow(e.rowIndex);

                        // Display custom message after deletion
                        DevExpress.ui.notify("삭제 되었습니다..", "success", 2000);
                    } else {
                        // Deletion canceled by the user
                        DevExpress.ui.notify("취소 되었습니다..", "info", 2000);
                    }
                });
            },
        });
    }

});