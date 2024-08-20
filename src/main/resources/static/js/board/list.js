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

        // $.ajax({
        //     url: '/board/list',
        //     method: 'GET',
        //     success: function (boards) {
                
        //         console.log('Boards loaded:', boards);

        //         // $('#board-list').empty();
        //         // boards.forEach(function (board) {
        //         //     let boardHtml = `
        //         //         <li class="board" data-id="${board.id}" data-due="${board.regDate}">
        //         //             <span class="board-title ${board.title ? 'completed' : ''}">${board.title}</span>
        //         //             <span class="baord-content">Due: ${borad.content}</span>
        //         //             <span class="board-regDate">${borad.regDate}</span>
        //         //             <span class="delete-task">X</span>
        //         //         </li>
        //         //     `;
        //         //     $('#board-list').append(boardHtml);
        //         // });
        //     },
        //     error: function (jqXHR, textStatus, errorThrown) {
        //         alert('Error loading tasks. Please try again.');
        //         console.error('Error loading tasks:', textStatus, errorThrown);
        //     }
        // });
    }
});