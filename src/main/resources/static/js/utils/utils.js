// Custom notification function
/**
 * Show a custom notification
 * @param message
 * @param type: "success", "warning", "error", "info"
 */

// make enum for type
export const NotificationType = {
    SUCCESS: "success",
    WARNING: "warning",
    ERROR: "error",
    INFO: "info"
};

export const DateFormat = {
    YYYY_MM_DD: 'yyyy-mm-dd',
    YYYY_MM: 'yyyy-mm',
    YYYYMM: 'yyyymm',
    YYYY: 'yyyy',
    MM: 'mm',
    DD: 'dd',
};

export function showNotification(message, type) {
    DevExpress.ui.notify({
        contentTemplate: function(element) {
             let content = `
                <div class="custom-notify">
                    <div class="notify-header">
                    <!--  <img src="/images/kzinc01.png" -->
                    <!--       alt="Company Logo" -->
                    <!--       class="company-logo"-->
                    <!--       cover="fit"-->
                    <!--       width="50"-->
                    <!--       height="50">-->
                        <span class="notify-title">${type.toUpperCase()}</span>
                    </div>
                    <div class="notify-body">
                        <span class="notify-icon ${type}-icon"></span>
                        <span class="notify-message">${message}</span>
                    </div>
                </div>
            `;
            element.append(content);
        },
        type: type, // "success", "warning", "error", "info"
        displayTime: 1000,
        position: {
            my: "center",
            at: "center",
            of: window
        },
        width: 400,
        height: "auto",
        animation: {
            show: {
                type: "fade",
                duration: 400,
                from: 0,
                to: 1
            },
            hide: {
                type: "fade",
                duration: 400,
                from: 1,
                to: 0
            }
        },
        shading: true,
        shadingColor: "rgba(0, 0, 0, 0.4)",
        closeOnClick: true
    });
}

// Helper functions to format phone numbers and SSNs
export function formatPhoneNumber(value) {
    if(!value) return "";
    let digits = value.replace(/\D/g, "");                        // Remove non-numeric characters
    return digits.replace(/(\d{3})(\d{3})(\d{4})/, "($1) $2-$3"); // Apply format (xxx) xxx-xxxx
}

export function formatSSN(value) {
    if(!value) return "";
    let digits = value.replace(/\D/g, "");            // Remove non-numeric characters
    return digits.replace(/(\d{6})(\d{7})/, '$1-$2'); // Apply format xxxxxx-xxxxxxx
}

/**
 * Format date to yyyy-mm-dd
 * @param value: ex) 2021-08-01T00:00:00.000Z or 2021-08-01 or 2021-08-01T00:00:00
 * @returns {string}: ex: 2021-08-01
 */
export function formatDateYyyyMmdd(value) {
    if(!value) return "";
    return new Date(value).toISOString().slice(0, 10); // Apply format yyyy-mm-dd
}

/**
 * Format date to yyyy-mm
 * @param value: ex: 2021-08-01T00:00:00.000Z or 2021-08-01 or 2021-08-01T00:00:00
 * @returns {*|string}: ex: 2021-08
 */
export function getYyyyMmDdFromValueWithFormat(value, format = DateFormat.YYYY_MM_DD) {
    if(!value) return "";

    const date = new Date(value);
    const isoDateString = date.toISOString();

    switch (format) {
        case DateFormat.YYYY_MM_DD:
            return isoDateString.slice(0, 10); // Format: yyyy-mm-dd

        case DateFormat.YYYY_MM:
            return isoDateString.slice(0, 7); // Format: yyyy-mm

        case DateFormat.YYYYMM:
            return isoDateString.slice(0, 7).replace(/-/g, ''); // Format: yyyymm

        case DateFormat.YYYY:
            return isoDateString.slice(0, 4); // Format: yyyy

        case DateFormat.MM:
            return isoDateString.slice(5, 7); // Format: mm

        case DateFormat.DD:
            return isoDateString.slice(8, 10); // Format: dd

        default:
            throw new Error(`Unsupported format: ${format}`);
    }
}

export function exportDataGridToExcel(dataGrid, xlsxFileName) {
    if (xlsxFileName) {
        // Use the default file name
        if (!xlsxFileName.includes('.xlsx')) {
            xlsxFileName += '.xlsx';
        }
    } else {
        // Use the file name entered by the user
        showNotification("Export excel file name 필요해요...", NotificationType.WARNING);
        return;
    }

    const workbook = new ExcelJS.Workbook();
    const worksheet = workbook.addWorksheet(xlsxFileName.replace('.xlsx', ''));

    DevExpress.excelExporter.exportDataGrid({
        component: dataGrid, // e.component
        worksheet,
        //autoFilterEnabled: true,
    }).then(() => {
        // Customize the exported Excel file
        console.log('Exported to Excel...onExporting() Manipulating..: ');
        const A1 = worksheet.getCell('A1');
        A1.value = '번호';
        A1.font = {bold: true};
        A1.alignment = {horizontal: 'center'};
        A1.fill = {
            type: 'pattern',
            pattern: 'solid',
            fgColor: {argb: 'FFD3D3D3'} // Light gray
        };
        // Apply styles to header cells
        worksheet.getRow(1).eachCell((cell) => {
            cell.font = {bold: true};
            cell.alignment = {horizontal: 'center'};
            cell.fill = {
                type: 'pattern',
                pattern: 'solid',
                fgColor: {argb: 'FFD3D3D3'} // Light gray
            };
        });
        // Format data cells
        worksheet.eachRow((row, rowNumber) => {
            if (rowNumber === 1) return; // Skip the header row
            row.eachCell((cell, colNumber) => {
                if (colNumber === 5) {
                    cell.value = formatPhoneNumber(cell.value);
                    cell.font = {color: {argb: 'FF0000FF'}, bold: true}; // Blue
                    cell.alignment = {horizontal: 'center'};
                } else if (colNumber === 6) {
                    cell.value = formatSSN(cell.value);
                } else if (colNumber === 3) {
                    cell.alignment = {horizontal: 'center'};
                }
                if (colNumber === 3) {
                    cell.font = {color: {argb: 'FF008000'}, bold: true}; // Green
                    cell.fill = {
                        type: 'pattern',
                        pattern: 'solid',
                        fgColor: {argb: 'FFFFC000'} // Light orange
                    };
                }
            });
        });
        // Save the workbook
        return workbook.xlsx.writeBuffer();
    }).then((buffer) => {
        console.log('Exported to Excel...exportDataGrid() : ');
        let message = `Exported to ${xlsxFileName} successfully.`;
        saveAs(new Blob([buffer], {type: 'application/octet-stream'}), xlsxFileName);
        showNotification(message, NotificationType.SUCCESS);
    }).catch((error) => {
        console.error('Error exporting to Excel: ', error);
        let message = "Error exporting to Excel: " + error;
        showNotification(message, NotificationType.ERROR);
        return Promise.reject(message);
    });
}

/**
 * Show a custom dialog with a prompt to enter a value(for the exporting file Name) and return the entered value
 * @param title
 * @param message
 * @param defaultValue
 * @returns {Promise<unknown>}
 */
export function showPromptDialog(title, message, defaultValue) {
    console.log("showPromptDialog function called");
    return new Promise(function(resolve) {
        console.log("Creating popup");

        let textBoxInstance;

        try {
            const $popup = $('<div>').appendTo('body');  // Append to body explicitly
            const popup = $popup.dxPopup({
                title: title,
                contentTemplate: function(contentElement) {
                    console.log("Content template function called");
                    const $textBox = $('<div>').dxTextBox({
                        value: defaultValue,
                        onValueChanged: function(e) {
                            console.log("TextBox value changed:", e.value);
                        }
                    });

                    textBoxInstance = $textBox.dxTextBox('instance');
                    contentElement.append(
                        $('<p>').text(message),
                        $textBox
                    );
                },
                width: 300,
                height: 'auto',
                visible: false,  // Start as invisible
                zIndex: 9999,
                onShown: function() {
                    console.log("Popup shown");
                    textBoxInstance && textBoxInstance.focus();
                },
                toolbarItems: [{
                    widget: 'dxButton',
                    toolbar: 'bottom',
                    location: 'after',
                    options: {
                        text: 'OK',
                        onClick: function() {
                            const textBoxValue = textBoxInstance.option('value');
                            console.log("OK clicked, value:", textBoxValue);
                            popup.hide();
                            resolve(textBoxValue);
                        }
                    }
                }, {
                    widget: 'dxButton',
                    toolbar: 'bottom',
                    location: 'after',
                    options: {
                        text: 'Cancel',
                        onClick: function() {
                            console.log("Cancel clicked");
                            popup.hide();
                            resolve(null);
                        }
                    }
                }]
            }).dxPopup("instance");

            console.log("Popup created, attempting to show");
            popup.show();  // Explicitly show the popup

        } catch (error) {
            console.error("Error creating or showing popup:", error);
            resolve(null);
        }
    });
}


export function headerStyleCenter (header, info) {
    $("<div>").css({ "text-align": "center", "width": "100%" })
        .text(info.column.caption)
        .appendTo(header);
}
