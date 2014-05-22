var gameId;
var currentPlayerNumber;
var fromPile;

function getTopCard(pile) {
    var topCard = pile.cards.pop();
    return topCard ? topCard : "EMPTY";
}

function allowDrop(event) {
    event.preventDefault();
}

function draw(game) {
    gameId = game.id;
    currentPlayerNumber = game.currentPlayerNumber;
    var gamePiles = game.piles;
    var player1piles = game.players.ONE.piles;
    var player2piles = game.players.TWO.piles;

    $('#gameId').html(gameId);
    $('#currentPlayer').html(currentPlayerNumber);
    $('#build1').attr("src", "../img/" + getTopCard(gamePiles.BUILD1) + ".png");
    $('#build2').attr("src", "../img/" + getTopCard(gamePiles.BUILD2) + ".png");
    $('#build3').attr("src", "../img/" + getTopCard(gamePiles.BUILD3) + ".png");
    $('#build4').attr("src", "../img/" + getTopCard(gamePiles.BUILD4) + ".png");

    $('#player1drawSize').html(player1piles.DRAW.cards.length);
    $('#player1draw').attr("src", "../img/" + getTopCard(player1piles.DRAW) + ".png");
    $('#player1hand1').attr("src", "../img/" + getTopCard(player1piles.HAND1) + ".png");
    $('#player1hand2').attr("src", "../img/" + getTopCard(player1piles.HAND2) + ".png");
    $('#player1hand3').attr("src", "../img/" + getTopCard(player1piles.HAND3) + ".png");
    $('#player1hand4').attr("src", "../img/" + getTopCard(player1piles.HAND4) + ".png");
    $('#player1hand5').attr("src", "../img/" + getTopCard(player1piles.HAND5) + ".png");
    $('#player1discard1').attr("src", "../img/" + getTopCard(player1piles.DISCARD1) + ".png");
    $('#player1discard2').attr("src", "../img/" + getTopCard(player1piles.DISCARD2) + ".png");
    $('#player1discard3').attr("src", "../img/" + getTopCard(player1piles.DISCARD3) + ".png");
    $('#player1discard4').attr("src", "../img/" + getTopCard(player1piles.DISCARD4) + ".png");

    $('#player2drawSize').html(player2piles.DRAW.cards.length);
    $('#player2draw').attr("src", "../img/" + getTopCard(player2piles.DRAW) + ".png");
    $('#player2hand1').attr("src", "../img/" + getTopCard(player2piles.HAND1) + ".png");
    $('#player2hand2').attr("src", "../img/" + getTopCard(player2piles.HAND2) + ".png");
    $('#player2hand3').attr("src", "../img/" + getTopCard(player2piles.HAND3) + ".png");
    $('#player2hand4').attr("src", "../img/" + getTopCard(player2piles.HAND4) + ".png");
    $('#player2hand5').attr("src", "../img/" + getTopCard(player2piles.HAND5) + ".png");
    $('#player2discard1').attr("src", "../img/" + getTopCard(player2piles.DISCARD1) + ".png");
    $('#player2discard2').attr("src", "../img/" + getTopCard(player2piles.DISCARD2) + ".png");
    $('#player2discard3').attr("src", "../img/" + getTopCard(player2piles.DISCARD3) + ".png");
    $('#player2discard4').attr("src", "../img/" + getTopCard(player2piles.DISCARD4) + ".png");
}

$(document).ready(function() {
    $.ajax("startGame", {
        type: "POST",
        success: function(game) {
            draw(game);
        }
    });
    
    $('.card').on('drop', function(event) {
        event.preventDefault();
        var from = fromPile.split('-');
        var to = event.target.title.split('-');
        var toPileIsBuildPile = $(event.target).hasClass("build");
        
        if (from[0] === currentPlayerNumber && (toPileIsBuildPile || to[0] === currentPlayerNumber)) {
            var toPile = toPileIsBuildPile ? to[0] : to[1];
            
            $.ajax("playCard/" + gameId + "/" + currentPlayerNumber + "/" + from[1] + "/" + toPile, {
                type: "POST",
                success: function(response) {
                    draw(response.game);
                }
            });
        }
    });
    
    $('.card').on('dragstart', function(event) {
        fromPile = event.target.title;
    });
});