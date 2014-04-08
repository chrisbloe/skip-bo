function getTopCard(pile) {
    var topCard = pile.cards.pop();
    return topCard ? topCard : "EMPTY";
}

$(document).ready(function() {
    $.ajax("startGame", {
        type: "POST",
        success: function(result) {
            var gamePiles = result.piles;
            var player1piles = result.players.ONE.piles;
            var player2piles = result.players.TWO.piles;
            
            $('#gameId').html(result.id);
            $('#currentPlayer').html(result.currentPlayerNumber);
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
    });
});