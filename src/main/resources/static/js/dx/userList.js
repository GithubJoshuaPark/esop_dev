import {showCustomNotification, NotificationType , formatSSN, formatPhoneNumber } from "../utils/utils.js";

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
                                let response_ = response.data;
                                console.log('response', response_);
                                return response_;
                            })
                            .catch(error => {
                                console.error("Error loading entities: ", error);
                                let message = "Error loading entities: " + error;
                                showCustomNotification(message, NotificationType.ERROR);
                                return Promise.reject(message);
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
                        showCustomNotification("Entity inserted successfully.", NotificationType.SUCCESS);
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

                return axios.put("/api/v1/dx/userList/fordx/" + key, updatedData, {
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
                return axios.delete("/api/v1/dx/userList/fordx/" + key, {
                        withCredentials: true // Send cookies when calling the API
                    })
                    .then(response => {
                        let response_ = response.data;
                        console.log('response', response_);
                        showCustomNotification("Entity deleted successfully.", NotificationType.SUCCESS);
                    })
                    .catch(error => {
                        console.error("Error deleting entity: ", error);
                        let message = "Error deleting entity: " + error;
                        showCustomNotification(message, NotificationType.ERROR);
                        return Promise.reject(message);
                    });
            }
        });

        $("#gridContainer").dxDataGrid({
            dataSource: dxDataSource,
            key: "id",
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
                mode: "form",         // edit mode: "form", "popup", "batch"
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
            onExporting(e) {
                // Customize the exported Excel file
                console.log('Exporting to Excel...onExporting() : ');
                e.cancel = true; // Prevent the default export action

                let xlsxFileName = prompt('Enter a excel file name', 'userList.xlsx');

                if (xlsxFileName) {
                    // Use the default file name
                    if(!xlsxFileName.includes('.xlsx')) {
                        xlsxFileName += '.xlsx';
                    }
                    e.component.option("export.fileName", xlsxFileName);
                }
                else {
                    // Use the file name entered by the user
                    showCustomNotification("Export excel file name 필요해요...", NotificationType.WARNING);
                    return;
                }

                const workbook = new ExcelJS.Workbook();
                const worksheet = workbook.addWorksheet(xlsxFileName.replace('.xlsx', ''));

                DevExpress.excelExporter.exportDataGrid({
                    component: e.component,
                    worksheet,
                    //autoFilterEnabled: true,
                }).then(() => {
                    // Customize the exported Excel file
                    console.log('Exported to Excel...onExporting() Manipulating..: ');
                    const A1 = worksheet.getCell('A1');
                    A1.value = '번호';
                    A1.font = { bold: true };
                    A1.alignment = { horizontal: 'center' };
                    A1.fill = {
                        type: 'pattern',
                        pattern: 'solid',
                        fgColor: { argb: 'FFD3D3D3' } // Light gray
                    };
                    // Apply styles to header cells
                    worksheet.getRow(1).eachCell((cell) => {
                        cell.font = { bold: true };
                        cell.alignment = { horizontal: 'center' };
                        cell.fill = {
                            type: 'pattern',
                            pattern: 'solid',
                            fgColor: { argb: 'FFD3D3D3' } // Light gray
                        };
                    });
                    // Format data cells
                    worksheet.eachRow((row, rowNumber) => {
                        if(rowNumber === 1) return; // Skip the header row
                        row.eachCell((cell, colNumber) => {
                            if(colNumber === 5) {
                                cell.value = formatPhoneNumber(cell.value);
                                cell.font = { color: { argb: 'FF0000FF' }, bold: true }; // Blue
                                cell.alignment = { horizontal: 'center' };
                            } else if (colNumber === 6) {
                                cell.value = formatSSN(cell.value);
                            } else if (colNumber === 3) {
                                cell.alignment = { horizontal: 'center' };
                            }
                            if(colNumber === 3) {
                                cell.font = { color: { argb: 'FF008000' }, bold: true }; // Green
                                cell.fill = {
                                    type: 'pattern',
                                    pattern: 'solid',
                                    fgColor: { argb: 'FFFFC000' } // Light orange
                                };
                            }
                        });
                    });
                    // Save the workbook
                    return workbook.xlsx.writeBuffer();
                }).then((buffer) => {
                    console.log('Exported to Excel...exportDataGrid() : ');
                    let message = `Exported to ${xlsxFileName} successfully.`;
                    saveAs(new Blob([buffer], { type: 'application/octet-stream' }), xlsxFileName);
                    showCustomNotification(message, NotificationType.SUCCESS);
                }).catch((error) => {
                    console.error('Error exporting to Excel: ', error);
                    let message = "Error exporting to Excel: " + error;
                    showCustomNotification(message, NotificationType.ERROR);
                    return Promise.reject(message);
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
                e.cancel = true; // Prevent the default deletion action until confirmed

                let dialogResult = DevExpress.ui.dialog.confirm("삭제할 거니?", "삭제 확인");
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