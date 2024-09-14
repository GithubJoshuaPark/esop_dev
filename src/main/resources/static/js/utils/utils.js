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

export function showCustomNotification(message, type) {
    DevExpress.ui.notify({
        contentTemplate: function(element) {
            var content = `
                <div class="custom-notify">
                    <div class="notify-header">
                        <img src="/images/company-logo.png" 
                             alt="Company Logo" 
                             class="company-logo"
                             cover="fit"
                             width="50"
                             height="50">
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
        displayTime: 3000,
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