import {
    showNotification,
    NotificationType,
    DateFormat,
    formatSSN,
    formatPhoneNumber,
    exportDataGridToExcel,
    showPromptDialog,
    formatDateYyyyMmdd,
    getYyyyMmDdFromValueWithFormat
} from "../utils/utils.js";

$(document).ready(function() {
    console.log("list module...");

    let activePopup = null; // Global variable to store the active popup
    let endPointUrl = "/api/v1/dx/stockBuyList/fordx";
    
    loadStockBuyList();

    function loadStockBuyList() {
        console.log('Loading Users...');

        // Create a container for the stepper
        //let stepperContainerAboveDataGrid = $("<div>").attr("id", "stepperContainerAboveDataGrid").insertBefore("#gridContainer");

        let dxDataSource = new DevExpress.data.CustomStore({
            key: "id",
            load: function() {
                return axios.get(endPointUrl, {})
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
                return axios.post(endPointUrl, values, {
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

                console.log('updating....', updatedData);

                return axios.put(endPointUrl + "/" + key, updatedData, {
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
                return axios.delete(endPointUrl + "/" + key, {
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
                { dataField: "id"           , allowEditing: false, visible: false },
                { dataField: "reqDt"        , caption: "요청일"   , dataType: "date"  , format: formatDateYyyyMmdd },
                { dataField: "curBal"       , caption: "현재 잔고", dataType: "number", format: "#,##0.##" },
                { dataField: "curStockPrice", caption: "현재 주가", dataType: "number", format: "#,##0.##" },
                { dataField: "reqQty"       , caption: "요청 수량", dataType: "number", format: "#,##0"    },
                { dataField: "reqAmt"       , caption: "요청 금액", dataType: "number", format: "#,##0.##" },
                {
                    dataField: "afterBal",
                    caption: "거래 후 잔고",
                    dataType: "number", // "number", "string", "date", "boolean", "object", "datetime", "datetimeoffset", "time"
                    defaultValue: 0,
                    format: "#,##0",
                },
                {
                    dataField: "status",
                    caption: "상태",
                    dataType: "string",
                    lookup: {
                        dataSource: [
                            { value: "W", text: "대기 (Wait)" },
                            { value: "I", text: "입력 (Input)" },
                            { value: "C", text: "승인 (Confirm)" },
                            { value: "F", text: "완료 (Finished)" },
                        ],
                        valueExpr: "value",
                        displayExpr: "text"
                    }
                },
                {
                    type: "buttons",
                    width: 100,
                    buttons: [
                        "edit",
                        "delete",
                        {
                            hint: "Detail",
                            icon: "info",
                            visible: true,
                            onClick: function(e) {
                                console.log('Detail button clicked e:', e.row);
                                showDetailPopup(e.row.data);
                            }
                        },
                    ],
                    visible: (e) => {
                        // Hide buttons if there's no data
                        return e.row && e.row.data && Object.keys(e.row.data).length > 0;
                    }
                },
            ],
            editing: {
                mode: "form",         // edit mode: "form", "popup", "batch"
                popup: { // Customize the popup layout and appearance when it comes to editing with a popup mode
                    title: "Stock Buy Info",
                    showTitle: true,
                    width: 800,
                    height: 525,
                },
                form: { // Customize the form layout and appearance when it comes to editing with a form mode
                    colCount: 2,
                    items: [
                        {
                            dataField: "reqDt",
                            label: { text: "요청일" },
                            editorType: "dxDateBox",
                            defaultValue: new Date(),
                            format: formatDateYyyyMmdd,
                        },
                        {
                            dataField: "curBal",
                            label: { text: "현재 잔고" },
                            editorType: "dxNumberBox",
                            editorOptions: {
                                format: "#,##0",
                            },
                            validationRules: [{ type: "required" }],
                        },
                        {
                            dataField: "curStockPrice",
                            label: { text: "현재 주가" },
                            editorType: "dxNumberBox",
                            editorOptions: {
                                format: "#,##0",
                            },
                            validationRules: [{ type: "required" }],
                        },
                        {
                            dataField: "reqQty",
                            label: { text: "요청 수량" },
                            editorType: "dxNumberBox",
                            editorOptions: {
                                format: "#,##0",
                            },
                            validationRules: [{ type: "required" }],
                        },
                        {
                            dataField: "reqAmt",
                            label: { text: "요청 금액" },
                            editorType: "dxNumberBox", // "dxAutocomplete", "dxLookup", "dxSelectBox", "dxTagBox", "dxTextArea", "dxTextBox", "dxNumberBox", "dxDateBox", "dxCheckBox", "dxSwitch", "dxRadioGroup", "dxSlider", "dxRangeSlider", "dxButtonGroup", "dxCalendar", "dxColorBox", "dxDateBox", "dxHtmlEditor", "dxLookup", "dxRadioGroup", "dxSelectBox", "dxTagBox", "dxTextArea", "dxTextBox", "dxCheckBox", "dxSwitch", "dxSlider", "dxRangeSlider", "dxButtonGroup", "dxCalendar", "dxColorBox", "dxDateBox", "dxHtmlEditor"
                            editorOptions: {
                                format: "#,##0",
                            },
                            validationRules: [{ type: "required" }],
                        },
                        {
                            dataField: "afterBal",
                            label: { text: "거래 후 잔고" },
                            editorType: "dxNumberBox", // "dxAutocomplete", "dxLookup", "dxSelectBox", "dxTagBox", "dxTextArea", "dxTextBox", "dxNumberBox", "dxDateBox", "dxCheckBox", "dxSwitch", "dxRadioGroup", "dxSlider", "dxRangeSlider", "dxButtonGroup", "dxCalendar", "dxColorBox", "dxDateBox", "dxHtmlEditor", "dxLookup", "dxRadioGroup", "dxSelectBox", "dxTagBox", "dxTextArea", "dxTextBox", "dxCheckBox", "dxSwitch", "dxSlider", "dxRangeSlider", "dxButtonGroup", "dxCalendar", "dxColorBox", "dxDateBox", "dxHtmlEditor"
                            editorOptions: {
                                format: "#,##0",
                            },
                            validationRules: [{ type: "required" }],
                        },
                        {
                            dataField: "status",
                            label: { text: "상태" },
                            editorType: "dxSelectBox",
                            editorOptions: {
                                lookup: {
                                    dataSource: [
                                        { value: "W", text: "대기 (Wait)" },
                                        { value: "I", text: "입력 (Input)" },
                                        { value: "C", text: "승인 (Confirm)" },
                                        { value: "F", text: "완료 (Finished)" },
                                    ],
                                    valueExpr: "value",
                                    displayExpr: "text"
                                }
                            },
                            validationRules: [{ type: "required" }],
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
            onContentReady: function(e) {
                let firstRow = e.component.getDataSource().items()[0];
                if(firstRow && firstRow.reqDt) {
                    console.log('firstRow.reqDt', firstRow.reqDt);
                    initializeStepper(firstRow.reqDt);
                }
            },
            onInitNewRow: function(e) {
                // Set default values for new rows
                e.data = {
                    reqAmt: 0,
                    reqDt: new Date(),
                    reqQty: 0,
                    status: "W", // Default status is "W" (Wait)
                };
            },
            onRowInserting: function(e) {
                // Ensuure the data object exists
                e.data = e.data || {};
                console.log('inserting....', e.data);
            },
            onRowInserted: function(e) {
                initializeStepper(e.data.reqDt);
            },
            onExporting(e) {
                // Customize the exported Excel file
                console.log('Exporting to Excel...onExporting() : ');
                e.cancel = true; // Prevent the default export action

                // Show a prompt dialog to get the file name
                let defaultFileName = 'stockBuyList_' + new Date().toISOString().slice(0,10); // e.g., userList_2023-10-14
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
                            return $("<div>").addClass("toolbar-header").text("Stock Buy List");
                        }
                    }
                );
            }, // Customize the toolbar
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

        initializeStepper(new Date()); // Initialize stepper with current date

        // Apply styling for stepper container
        $("<style>")
            .prop("type", "text/css")
            .html(`
                #stockBuyWrapper {
                    position: relative;
                    display: flex;
                    flex-direction: column;
                }
                #stepperContainerAimport {
    showNotification,
    NotificationType,
    DateFormat,
    formatSSN,
    formatPhoneNumber,
    exportDataGridToExcel,
    showPromptDialog,
    formatDateYyyyMmdd,
    getYyyyMmDdFromValueWithFormat
} from "../utils/utils.js";

$(document).ready(function() {
    console.log("list module...");

    let activePopup = null; // Global variable to store the active popup
    let endPointUrl = "/api/v1/dx/stockBuyList/fordx";
    
    loadStockBuyList();

    function loadStockBuyList() {
        console.log('Loading Users...');

        // Create a container for the stepper
        //let stepperContainerAboveDataGrid = $("<div>").attr("id", "stepperContainerAboveDataGrid").insertBefore("#gridContainer");

        let dxDataSource = new DevExpress.data.CustomStore({
            key: "id",
            load: function() {
                return axios.get(endPointUrl, {})
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
                return axios.post(endPointUrl, values, {
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

                console.log('updating....', updatedData);

                return axios.put(endPointUrl + "/" + key, updatedData, {
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
                return axios.delete(endPointUrl + "/" + key, {
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
                { dataField: "id"           , allowEditing: false, visible: false },
                { dataField: "reqDt"        , caption: "요청일"   , dataType: "date"  , format: formatDateYyyyMmdd },
                { dataField: "curBal"       , caption: "현재 잔고", dataType: "number", format: "#,##0.##" },
                { dataField: "curStockPrice", caption: "현재 주가", dataType: "number", format: "#,##0.##" },
                { dataField: "reqQty"       , caption: "요청 수량", dataType: "number", format: "#,##0"    },
                { dataField: "reqAmt"       , caption: "요청 금액", dataType: "number", format: "#,##0.##" },
                {
                    dataField: "afterBal",
                    caption: "거래 후 잔고",
                    dataType: "number", // "number", "string", "date", "boolean", "object", "datetime", "datetimeoffset", "time"
                    defaultValue: 0,
                    format: "#,##0",
                },
                {
                    dataField: "status",
                    caption: "상태",
                    dataType: "string",
                    lookup: {
                        dataSource: [
                            { value: "W", text: "대기 (Wait)" },
                            { value: "I", text: "입력 (Input)" },
                            { value: "C", text: "승인 (Confirm)" },
                            { value: "F", text: "완료 (Finished)" },
                        ],
                        valueExpr: "value",
                        displayExpr: "text"
                    }
                },
                {
                    type: "buttons",
                    width: 100,
                    buttons: [
                        "edit",
                        "delete",
                        {
                            hint: "Detail",
                            icon: "info",
                            visible: true,
                            onClick: function(e) {
                                console.log('Detail button clicked e:', e.row);
                                showDetailPopup(e.row.data);
                            }
                        },
                    ],
                    visible: (e) => {
                        // Hide buttons if there's no data
                        return e.row && e.row.data && Object.keys(e.row.data).length > 0;
                    }
                },
            ],
            editing: {
                mode: "form",         // edit mode: "form", "popup", "batch"
                popup: { // Customize the popup layout and appearance when it comes to editing with a popup mode
                    title: "Stock Buy Info",
                    showTitle: true,
                    width: 800,
                    height: 525,
                },
                form: { // Customize the form layout and appearance when it comes to editing with a form mode
                    colCount: 2,
                    items: [
                        {
                            dataField: "reqDt",
                            label: { text: "요청일" },
                            editorType: "dxDateBox",
                            defaultValue: new Date(),
                            format: formatDateYyyyMmdd,
                        },
                        {
                            dataField: "curBal",
                            label: { text: "현재 잔고" },
                            editorType: "dxNumberBox",
                            editorOptions: {
                                format: "#,##0",
                            },
                            validationRules: [{ type: "required" }],
                        },
                        {
                            dataField: "curStockPrice",
                            label: { text: "현재 주가" },
                            editorType: "dxNumberBox",
                            editorOptions: {
                                format: "#,##0",
                            },
                            validationRules: [{ type: "required" }],
                        },
                        {
                            dataField: "reqQty",
                            label: { text: "요청 수량" },
                            editorType: "dxNumberBox",
                            editorOptions: {
                                format: "#,##0",
                            },
                            validationRules: [{ type: "required" }],
                        },
                        {
                            dataField: "reqAmt",
                            label: { text: "요청 금액" },
                            editorType: "dxNumberBox", // "dxAutocomplete", "dxLookup", "dxSelectBox", "dxTagBox", "dxTextArea", "dxTextBox", "dxNumberBox", "dxDateBox", "dxCheckBox", "dxSwitch", "dxRadioGroup", "dxSlider", "dxRangeSlider", "dxButtonGroup", "dxCalendar", "dxColorBox", "dxDateBox", "dxHtmlEditor", "dxLookup", "dxRadioGroup", "dxSelectBox", "dxTagBox", "dxTextArea", "dxTextBox", "dxCheckBox", "dxSwitch", "dxSlider", "dxRangeSlider", "dxButtonGroup", "dxCalendar", "dxColorBox", "dxDateBox", "dxHtmlEditor"
                            editorOptions: {
                                format: "#,##0",
                            },
                            validationRules: [{ type: "required" }],
                        },
                        {
                            dataField: "afterBal",
                            label: { text: "거래 후 잔고" },
                            editorType: "dxNumberBox", // "dxAutocomplete", "dxLookup", "dxSelectBox", "dxTagBox", "dxTextArea", "dxTextBox", "dxNumberBox", "dxDateBox", "dxCheckBox", "dxSwitch", "dxRadioGroup", "dxSlider", "dxRangeSlider", "dxButtonGroup", "dxCalendar", "dxColorBox", "dxDateBox", "dxHtmlEditor", "dxLookup", "dxRadioGroup", "dxSelectBox", "dxTagBox", "dxTextArea", "dxTextBox", "dxCheckBox", "dxSwitch", "dxSlider", "dxRangeSlider", "dxButtonGroup", "dxCalendar", "dxColorBox", "dxDateBox", "dxHtmlEditor"
                            editorOptions: {
                                format: "#,##0",
                            },
                            validationRules: [{ type: "required" }],
                        },
                        {
                            dataField: "status",
                            label: { text: "상태" },
                            editorType: "dxSelectBox",
                            editorOptions: {
                                lookup: {
                                    dataSource: [
                                        { value: "W", text: "대기 (Wait)" },
                                        { value: "I", text: "입력 (Input)" },
                                        { value: "C", text: "승인 (Confirm)" },
                                        { value: "F", text: "완료 (Finished)" },
                                    ],
                                    valueExpr: "value",
                                    displayExpr: "text"
                                }
                            },
                            validationRules: [{ type: "required" }],
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
            onContentReady: function(e) {
                let firstRow = e.component.getDataSource().items()[0];
                if(firstRow && firstRow.reqDt) {
                    console.log('firstRow.reqDt', firstRow.reqDt);
                    initializeStepper(firstRow.reqDt);
                }
            },
            onInitNewRow: function(e) {
                // Set default values for new rows
                e.data = {
                    reqAmt: 0,
                    reqDt: new Date(),
                    reqQty: 0,
                    status: "W", // Default status is "W" (Wait)
                };
            },
            onRowInserting: function(e) {
                // Ensuure the data object exists
                e.data = e.data || {};
                console.log('inserting....', e.data);
            },
            onRowInserted: function(e) {
                initializeStepper(e.data.reqDt);
            },
            onExporting(e) {
                // Customize the exported Excel file
                console.log('Exporting to Excel...onExporting() : ');
                e.cancel = true; // Prevent the default export action

                // Show a prompt dialog to get the file name
                let defaultFileName = 'stockBuyList_' + new Date().toISOString().slice(0,10); // e.g., userList_2023-10-14
                //let fileName = prompt("Enter a file name:", defaultFileName);
                showPromptDialog("Export to Excel", "Enter a file name:", defaultFileName)
                    .then((fileName) => {
                        if (fileName) {
                            // Remove invalid characters from file name
                            fileName = fileName.replace(/[/\\\\?%*:|"<>]/g, '-').trim();
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
                            return $("<div>").addClass("toolbar-header").text("Stock Buy List");
                        }
                    }
                );
            }, // Customize the toolbar
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

        initializeStepper(new Date()); // Initialize stepper with current date

        // Apply styling for stepper container
        $("<style>")
            .prop("type", "text/css")
            .html(\`
                #stockBuyWrapper {
                    display: flex;
                    flex-direction: column;
                }
                #stepperContainerAboveDataGrid {
                    align-self: flex-end;
                    margin-bottom: 10px;
                }
                #gridContainer {
                    flex-grow: 1;
                    width: calc(100% - 320px); /* Adjust based on stepper width + margin */
                }
            \`)
            .appendTo("head");

    } // End: loadBoards()

    function initializeStepper(reqDt) {
        // Make API call to get current status
        let reqDtYyyyMm_ = getYyyyMmDdFromValueWithFormat(reqDt, DateFormat.YYYYMM);
        console.log('initializeStepper.reqDtYyyyMm_: ', reqDtYyyyMm_);

        axios.get(\`/api/v1/dx/stockBuyList/fordx/getDxStockBuyByReqDt/${reqDtYyyyMm_}\`, {})
            .then(response => {
                console.log('initializeStepper.response', response);
                let currentStatus = response.data.status; // Get the current status, e.g., "W", "I", "C", "F"
                createStepper(currentStatus);
            })
            .catch(error => {
                console.error("Error fetching status:", error);
                showNotification("Error fetching status", NotificationType.ERROR);
                createStepper("W"); // Create stepper with default status
            });
    }

    function createStepper(currentStatus) {
        let stepperItems = [
            { text: "대기 (Wait)", status: "W" },
            { text: "입력 (Input)", status: "I" },
            { text: "승인 (Confirm)", status: "C" },
            { text: "완료 (Finished)", status: "F" }
        ];

        $("#stepperContainerAboveDataGrid").dxButtonGroup({
            items: stepperItems,
            keyExpr: "status",
            selectedItemKeys: [currentStatus],
            stylingMode: "outlined",
            selectionMode: "single",
            disabled: true, // Make it read-only
            onItemClick: function(e) {
                // No action on click as it's read-only
            }
        });
    }

    function showDetailPopup(rowDataFr_) {
        console.log('showDetailPopup', rowDataFr_);

        // Close any existing popup
        if (activePopup) {
            activePopup.hide();
        }

        // Create a container for the popup
        let $popupContainer = $("<div>").appendTo("body");
            let popupContent    = $("<div>").appendTo($popupContainer).addClass("popup-content");
                //let tabsContainer   = $("<div>").appendTo(popupContent).addClass("tabs-container");
                let stepperContainer = $("<div>").appendTo(popupContent).addClass("stepper-container");
                let sliderContainer = $("<div>").appendTo(popupContent).addClass("slider-container");
                let formContainer   = $("<div>").appendTo(popupContent).addClass("form-container");
            let buttonContainer = $("<div>").appendTo($popupContainer).addClass("button-container");

        // Create a popup to display the details
        activePopup = $popupContainer.dxPopup({
            title: "Stock Buy Detail",
            showTitle: true,
            width: 800,
            height: 400,
            onHidden: function() {
                $popupContainer.remove(); // Remove the popup container from the DOM
                activePopup = null;
            }
        }).dxPopup("instance"); // End of popup

        // Create tabs for the status
        // let tabs = tabsContainer.dxTabs({
        //     dataSource: [
        //         { text: "대기 (Wait)", status: "W" },  // itemData
        //         { text: "입력 (Input)", status: "I" },
        //         { text: "승인 (Confirm)", status: "C" },
        //         { text: "완료 (Finished)", status: "F" }
        //     ],
        //     visible: true,
        //     selectedIndex: getTabIndexFromStatus(rowDataFr_.status),
        //     onItemClick: function(e) {
        //         console.log('onItemClick', e.itemData);
        //         let rowDataFr__ = Object.assign({}, rowDataFr_, { status: e.itemData.status });
        //         console.log('rowDataFr__', rowDataFr__);
        //         updateStockBuyStatus(rowDataFr_.id, rowDataFr__);
        //     },
        //     itemTemplate: function(itemData, itemIndex, itemElement) {
        //         itemElement.addClass("custom-tab");
        //         return itemData.text;
        //     },
        //     selectedItemTemplate: function(itemData, itemIndex, itemElement) {
        //         itemElement.addClass("custom-tab selected-tab");
        //         return itemData.text;
        //     }
        // }).dxTabs("instance"); // End of tabs


        // Create stepperItems for the custom stepper using dxButtonGroup for the status
        const stepperItems = [
            { text: "대기 (Wait)"    , status: "W", hint: "Waiting for Stock buy " },  // itemData
            { text: "입력 (Input)"   , status: "I", hint: "Inputted qty for Stock buy" },
            { text: "승인 (Confirm)" , status: "C", hint: "Confirmed" },
            { text: "완료 (Finished)", status: "F", hint: "Finished"  },
        ];

        // Create a custom stepper using dxButtonGroup  for the status
        let stepper = stepperContainer.dxButtonGroup({
            items: stepperItems,
            keyExpr: "status",
            selectedItemKeys: [rowDataFr_.status],
            stylingMode: "outlined",
            selectionMode: "single",
            onItemClick: function(e) {
                console.log('onSelectionChanged', e);
                let rowDataFr__ = Object.assign({}, rowDataFr_, { status: e.itemData.status });
                console.log('rowDataFr__', rowDataFr__);
                updateStockBuyStatus(rowDataFr_.id, rowDataFr__);
            },
            onContentReady: function(e) {
                // Create tooltips for each stepper item after the buttons are rendered
                stepperItems.forEach((item, index) => {
                    let buttonElement = e.element.find(\`.dx-item:eq(${index})\`);
                    let tooltipContainer = $("<div>").appendTo(stepperContainer);

                    tooltipContainer.dxTooltip({
                        target: buttonElement,
                        position: "top",
                        showEvent: "dxhoverstart",
                        hideEvent: "dxhoverend",
                        contentTemplate: function() {
                            return item.hint;
                        }
                    });
                });
            }
        }).dxButtonGroup("instance");

        // Create statusItems for a slider to change the status
        const statusItems = [
            { value: 0, text: "대기", status: "W" },
            { value: 1, text: "입력", status: "I" },
            { value: 2, text: "승인", status: "C" },
            { value: 3, text: "완료", status: "F" }
        ];

        // Create a slider to change the status
        let slider = sliderContainer.dxSlider({
            min: 0,
            max: 3,
            value: statusItems.findIndex(item => item.status === rowDataFr_.status),
            step: 1,
            tooltip: {
                enabled: true,
                format: function(value) {
                    return statusItems[value].text;
                },
                showMode: "always",
                position: "top"
            },
            label: {
                visible: true,
                format: function(value) {
                    return statusItems[value].text;
                },
                position: "top"
            },
            onValueChanged: function(e) {
                let newStatus = statusItems[e.value].status;
                let updatedData = { ...rowDataFr_, status: newStatus };
                updateStockBuyStatus(rowDataFr_.id, updatedData);
            }
        }).dxSlider("instance");

        // Create a form to display the details
        let form = formContainer.dxForm({
            formData: rowDataFr_,
            readOnly: true,
            showColonAfterLabel: true,
            showValidationSummary: true,
            colCount: 2,
            items: [
                { dataField: "reqDt",  label: { text: "요청일"   }, editorType: "dxDateBox"  , editorOptions: {format: formatDateYyyyMmdd,},},
                { dataField: "reqQty", label: { text: "요청수량" }, editorType: "dxNumberBox", editorOptions: {format: "#,##0",},},
                { dataField: "reqAmt", label: { text: "요청금액" }, editorType: "dxNumberBox", editorOptions: {format: "#,##0",},},
                { dataField: "status", label: { text: "상태"     }, editorType: "dxSelectBox", editorOptions: {
                        items: [
                            { value: "W", text: "대기 (Wait)" },
                            { value: "I", text: "입력 (Input)" },
                            { value: "C", text: "승인 (Confirm)" },
                            { value: "F", text: "완료 (Finished)" },
                        ],
                        valueExpr: "value",
                        displayExpr: "text"
                    },
                },
            ]
        }).dxForm("instance"); // End of form

        // Create a button to close the popup
        let closeBtn = buttonContainer.dxButton({
            text: "Close",
            type: "default",
            onClick: function() {
                activePopup.hide();
            }
        }).dxButton("instance"); // End of closeBtn

        // Apply styling to position the button at the bottom right
        $("<style>")
            .prop("type", "text/css")
            .html(\`
                .popup-content {
                    height: calc(100% - 40px);
                    overflow-y: auto;
                }
                .tabs-container {
                    margin: 10px 0;                    
                }
                .form-container {
                    // margin: 10px;
                    // margin-top: 10px;
                }
                .stepper-container {
                    margin: 10px 0;
                }
                .slider-container {
                    margin: 10px 0;
                }
                .button-container {
                    position: absolute;
                    bottom: 10px;
                    right: 10px;
                }
                // .custom-tab {
                //     background-color: #f0f0f0;
                //     border: 1px solid #ddd;
                //     padding: 10px;
                //     transition: background-color 0.3s;
                // }
                // .custom-tab:hover {
                //     background-color: #e0e0e0;
                // }
                // .custom-tab
                // .selected-tab {
                //     background-color: #007bff !important; // Blue
                //     color: white !important;
                //     font-weight: bold;
                // }
                .dx-button-mode-outlined.dx-button-default {
                    background-color: transparent;
                    border-color: #ddd;
                    color: #333;
                }
                .dx-button-mode-outlined.dx-button-default.dx-button-has-text {
                    border-radius: 4px;
                }
                .dx-button-mode-outlined.dx-button-default.dx-state-hover {
                    background-color: #f5f5f5;
                }
                .dx-buttongroup-item.dx-button.dx-button-mode-outlined.dx-state-focused {
                    background-color: #007bff;
                    color: white;
                }
            \`)
            .appendTo("head");

        activePopup.show();
    } // End: showDetailPopup()

    //---------------------------------------------------------
    // MARK: - functions start
    function getTabIndexFromStatus(status) {
        const statusMap = { "W": 0, "I": 1, "C": 2, "F": 3 };
        return statusMap[status] || 0;
    }

    function updateStockBuyStatus(id, data) {
        console.log('updateStockBuyStatus', id, data);
        axios.put("/api/v1/dx/stockBuyList/fordx/" + id,
            { ...data },
            { withCredentials: true}, // Send cookies when calling the API
        ).then(response => {
            let response_ = response.data;
            console.log('response', response_);
            showNotification("Status updated successfully.", NotificationType.SUCCESS);
            $("#gridContainer").dxDataGrid("instance").refresh(); // Refresh the main grid

            // update the status in the detail popup
            if (activePopup) {
                // Update the formData.status in the form
                let form = activePopup.content().find(".form-container").dxForm("instance");
                if (form) {
                    form.option("formData.status", data.status);
                }

                // Update the stepper
                let stepper = activePopup.content().find(".stepper-container").dxButtonGroup("instance");
                if (stepper) {
                    stepper.option("selectedItemKeys", [data.status]);
                }

                // Update the slider
                let slider = activePopup.content().find(".slider-container").dxSlider("instance");
                if (slider) {
                    const statusItems = [
                        { value: 0, text: "대기", status: "W" },
                        { value: 1, text: "입력", status: "I" },
                        { value: 2, text: "승인", status: "C" },
                        { value: 3, text: "완료", status: "F" }
                    ];
                    slider.option("value", statusItems.findIndex(item => item.status === data.status));
                }
            }

        }).catch(error => {
            console.error("Error updating status: ", error);
            let message = "Error updating status: " + error;
            showNotification(message, NotificationType.ERROR);
        });
    }
    // MARK: - functions end
    //---------------------------------------------------------

}); // End: $(document).ready()boveDataGrid {
                    margin-bottom: 20px;
                    
                    // set the right side in the stockBuyWrapper
                    position: absolute;
                    right: 0;
                                                                      
                }
                #gridContainer {
                    flex-grow: 1;
                }
            `)
            .appendTo("head");

    } // End: loadBoards()

    function initializeStepper(reqDt) {
        // Make API call to get current status
        let reqDtYyyyMm_ = getYyyyMmDdFromValueWithFormat(reqDt, DateFormat.YYYYMM);
        console.log('initializeStepper.reqDtYyyyMm_: ', reqDtYyyyMm_);

        axios.get(`/api/v1/dx/stockBuyList/fordx/getDxStockBuyByReqDt/${reqDtYyyyMm_}`, {})
            .then(response => {
                console.log('initializeStepper.response', response);
                let currentStatus = response.data.status; // Get the current status, e.g., "W", "I", "C", "F"
                createStepper(currentStatus);
            })
            .catch(error => {
                console.error("Error fetching status:", error);
                showNotification("Error fetching status", NotificationType.ERROR);
                createStepper("W"); // Create stepper with default status
            });
    }

    function createStepper(currentStatus) {
        let stepperItems = [
            { text: "대기 (Wait)", status: "W" },
            { text: "입력 (Input)", status: "I" },
            { text: "승인 (Confirm)", status: "C" },
            { text: "완료 (Finished)", status: "F" }
        ];

        $("#stepperContainerAboveDataGrid").dxButtonGroup({
            items: stepperItems,
            keyExpr: "status",
            selectedItemKeys: [currentStatus],
            stylingMode: "outlined",
            selectionMode: "single",
            disabled: true, // Make it read-only
            onItemClick: function(e) {
                // No action on click as it's read-only
            }
        });
    }

    function showDetailPopup(rowDataFr_) {
        console.log('showDetailPopup', rowDataFr_);

        // Close any existing popup
        if (activePopup) {
            activePopup.hide();
        }

        // Create a container for the popup
        let $popupContainer = $("<div>").appendTo("body");
            let popupContent    = $("<div>").appendTo($popupContainer).addClass("popup-content");
                //let tabsContainer   = $("<div>").appendTo(popupContent).addClass("tabs-container");
                let stepperContainer = $("<div>").appendTo(popupContent).addClass("stepper-container");
                let sliderContainer = $("<div>").appendTo(popupContent).addClass("slider-container");
                let formContainer   = $("<div>").appendTo(popupContent).addClass("form-container");
            let buttonContainer = $("<div>").appendTo($popupContainer).addClass("button-container");

        // Create a popup to display the details
        activePopup = $popupContainer.dxPopup({
            title: "Stock Buy Detail",
            showTitle: true,
            width: 800,
            height: 400,
            onHidden: function() {
                $popupContainer.remove(); // Remove the popup container from the DOM
                activePopup = null;
            }
        }).dxPopup("instance"); // End of popup

        // Create tabs for the status
        // let tabs = tabsContainer.dxTabs({
        //     dataSource: [
        //         { text: "대기 (Wait)", status: "W" },  // itemData
        //         { text: "입력 (Input)", status: "I" },
        //         { text: "승인 (Confirm)", status: "C" },
        //         { text: "완료 (Finished)", status: "F" }
        //     ],
        //     visible: true,
        //     selectedIndex: getTabIndexFromStatus(rowDataFr_.status),
        //     onItemClick: function(e) {
        //         console.log('onItemClick', e.itemData);
        //         let rowDataFr__ = Object.assign({}, rowDataFr_, { status: e.itemData.status });
        //         console.log('rowDataFr__', rowDataFr__);
        //         updateStockBuyStatus(rowDataFr_.id, rowDataFr__);
        //     },
        //     itemTemplate: function(itemData, itemIndex, itemElement) {
        //         itemElement.addClass("custom-tab");
        //         return itemData.text;
        //     },
        //     selectedItemTemplate: function(itemData, itemIndex, itemElement) {
        //         itemElement.addClass("custom-tab selected-tab");
        //         return itemData.text;
        //     }
        // }).dxTabs("instance"); // End of tabs


        // Create stepperItems for the custom stepper using dxButtonGroup for the status
        const stepperItems = [
            { text: "대기 (Wait)"    , status: "W", hint: "Waiting for Stock buy " },  // itemData
            { text: "입력 (Input)"   , status: "I", hint: "Inputted qty for Stock buy" },
            { text: "승인 (Confirm)" , status: "C", hint: "Confirmed" },
            { text: "완료 (Finished)", status: "F", hint: "Finished"  },
        ];

        // Create a custom stepper using dxButtonGroup  for the status
        let stepper = stepperContainer.dxButtonGroup({
            items: stepperItems,
            keyExpr: "status",
            selectedItemKeys: [rowDataFr_.status],
            stylingMode: "outlined",
            selectionMode: "single",
            onItemClick: function(e) {
                console.log('onSelectionChanged', e);
                let rowDataFr__ = Object.assign({}, rowDataFr_, { status: e.itemData.status });
                console.log('rowDataFr__', rowDataFr__);
                updateStockBuyStatus(rowDataFr_.id, rowDataFr__);
            },
            onContentReady: function(e) {
                // Create tooltips for each stepper item after the buttons are rendered
                stepperItems.forEach((item, index) => {
                    let buttonElement = e.element.find(`.dx-item:eq(${index})`);
                    let tooltipContainer = $("<div>").appendTo(stepperContainer);

                    tooltipContainer.dxTooltip({
                        target: buttonElement,
                        position: "top",
                        showEvent: "dxhoverstart",
                        hideEvent: "dxhoverend",
                        contentTemplate: function() {
                            return item.hint;
                        }
                    });
                });
            }
        }).dxButtonGroup("instance");

        // Create statusItems for a slider to change the status
        const statusItems = [
            { value: 0, text: "대기", status: "W" },
            { value: 1, text: "입력", status: "I" },
            { value: 2, text: "승인", status: "C" },
            { value: 3, text: "완료", status: "F" }
        ];

        // Create a slider to change the status
        let slider = sliderContainer.dxSlider({
            min: 0,
            max: 3,
            value: statusItems.findIndex(item => item.status === rowDataFr_.status),
            step: 1,
            tooltip: {
                enabled: true,
                format: function(value) {
                    return statusItems[value].text;
                },
                showMode: "always",
                position: "top"
            },
            label: {
                visible: true,
                format: function(value) {
                    return statusItems[value].text;
                },
                position: "top"
            },
            onValueChanged: function(e) {
                let newStatus = statusItems[e.value].status;
                let updatedData = { ...rowDataFr_, status: newStatus };
                updateStockBuyStatus(rowDataFr_.id, updatedData);
            }
        }).dxSlider("instance");

        // Create a form to display the details
        let form = formContainer.dxForm({
            formData: rowDataFr_,
            readOnly: true,
            showColonAfterLabel: true,
            showValidationSummary: true,
            colCount: 2,
            items: [
                { dataField: "reqDt",  label: { text: "요청일"   }, editorType: "dxDateBox"  , editorOptions: {format: formatDateYyyyMmdd,},},
                { dataField: "reqQty", label: { text: "요청수량" }, editorType: "dxNumberBox", editorOptions: {format: "#,##0",},},
                { dataField: "reqAmt", label: { text: "요청금액" }, editorType: "dxNumberBox", editorOptions: {format: "#,##0",},},
                { dataField: "status", label: { text: "상태"     }, editorType: "dxSelectBox", editorOptions: {
                        items: [
                            { value: "W", text: "대기 (Wait)" },
                            { value: "I", text: "입력 (Input)" },
                            { value: "C", text: "승인 (Confirm)" },
                            { value: "F", text: "완료 (Finished)" },
                        ],
                        valueExpr: "value",
                        displayExpr: "text"
                    },
                },
            ]
        }).dxForm("instance"); // End of form

        // Create a button to close the popup
        let closeBtn = buttonContainer.dxButton({
            text: "Close",
            type: "default",
            onClick: function() {
                activePopup.hide();
            }
        }).dxButton("instance"); // End of closeBtn

        // Apply styling to position the button at the bottom right
        $("<style>")
            .prop("type", "text/css")
            .html(`
                .popup-content {
                    height: calc(100% - 40px);
                    overflow-y: auto;
                }
                .tabs-container {
                    margin: 10px 0;                    
                }
                .form-container {
                    // margin: 10px;
                    // margin-top: 10px;
                }
                .stepper-container {
                    margin: 10px 0;
                }
                .slider-container {
                    margin: 10px 0;
                }
                .button-container {
                    position: absolute;
                    bottom: 10px;
                    right: 10px;
                }
                // .custom-tab {
                //     background-color: #f0f0f0;
                //     border: 1px solid #ddd;
                //     padding: 10px;
                //     transition: background-color 0.3s;
                // }
                // .custom-tab:hover {
                //     background-color: #e0e0e0;
                // }
                // .custom-tab
                // .selected-tab {
                //     background-color: #007bff !important; // Blue
                //     color: white !important;
                //     font-weight: bold;
                // }
                .dx-button-mode-outlined.dx-button-default {
                    background-color: transparent;
                    border-color: #ddd;
                    color: #333;
                }
                .dx-button-mode-outlined.dx-button-default.dx-button-has-text {
                    border-radius: 4px;
                }
                .dx-button-mode-outlined.dx-button-default.dx-state-hover {
                    background-color: #f5f5f5;
                }
                .dx-buttongroup-item.dx-button.dx-button-mode-outlined.dx-state-focused {
                    background-color: #007bff;
                    color: white;
                }
            `)
            .appendTo("head");

        activePopup.show();
    } // End: showDetailPopup()

    //---------------------------------------------------------
    // MARK: - functions start
    function getTabIndexFromStatus(status) {
        const statusMap = { "W": 0, "I": 1, "C": 2, "F": 3 };
        return statusMap[status] || 0;
    }

    function updateStockBuyStatus(id, data) {
        console.log('updateStockBuyStatus', id, data);
        axios.put("/api/v1/dx/stockBuyList/fordx/" + id,
            { ...data },
            { withCredentials: true}, // Send cookies when calling the API
        ).then(response => {
            let response_ = response.data;
            console.log('response', response_);
            showNotification("Status updated successfully.", NotificationType.SUCCESS);
            $("#gridContainer").dxDataGrid("instance").refresh(); // Refresh the main grid

            // update the status in the detail popup
            if (activePopup) {
                // Update the formData.status in the form
                let form = activePopup.content().find(".form-container").dxForm("instance");
                if (form) {
                    form.option("formData.status", data.status);
                }

                // Update the stepper
                let stepper = activePopup.content().find(".stepper-container").dxButtonGroup("instance");
                if (stepper) {
                    stepper.option("selectedItemKeys", [data.status]);
                }

                // Update the slider
                let slider = activePopup.content().find(".slider-container").dxSlider("instance");
                if (slider) {
                    const statusItems = [
                        { value: 0, text: "대기", status: "W" },
                        { value: 1, text: "입력", status: "I" },
                        { value: 2, text: "승인", status: "C" },
                        { value: 3, text: "완료", status: "F" }
                    ];
                    slider.option("value", statusItems.findIndex(item => item.status === data.status));
                }
            }

        }).catch(error => {
            console.error("Error updating status: ", error);
            let message = "Error updating status: " + error;
            showNotification(message, NotificationType.ERROR);
        });
    }
    // MARK: - functions end
    //---------------------------------------------------------

}); // End: $(document).ready()