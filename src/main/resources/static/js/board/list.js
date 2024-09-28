$(document).ready(function() {
    console.log("list module...");
    
    loadBoards();

    $('.delete-btn').click(function() {
        let button = $(this);
        let boardId = button.data('board-id'); 
        if (confirm('Are you sure you want to delete this board?')) {
            $.ajax({
                url: '/api/v1/board/' + boardId,
                type: 'DELETE',
                xhrFields: {
                    withCredentials: true // Send cookies when calling the API
                },
                success: function(result) {
                    // Remove the table row
                    button.closest('tr').remove();
                    alert('Board deleted successfully');
                    window.location.href = '/board/list'; // Redirect to the board list page
                },
                error: function(xhr, status, error) {
                    alert('Error deleting board: ' + error);
                }
            });
        }
    });

    function loadBoards() {
        console.log('Loading loadBoards...');
    }

});