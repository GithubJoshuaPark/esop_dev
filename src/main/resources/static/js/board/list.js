$(document).ready(function() {
    console.log("list module...");
    
    loadBoards();

    // $('#board-write').on('click', function (e) {
    //     e.preventDefault();
    //     //addTask();
    //     console.log('addTask...');
    // });


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