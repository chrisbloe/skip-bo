<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
<!--        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">-->
        <link type="text/css" rel="stylesheet" href="../css/game.css?t=080414">
        <link type="text/css" rel="stylesheet" href="../js/libs/twitter-bootstrap/css/bootstrap.css?t=080414">
        <link type="text/css" rel="stylesheet" href="../js/libs/twitter-bootstrap/css/bootstrap-theme.css?t=080414">
        <script type="text/javascript" src="../js/libs/jquery/jquery.js?t=080414"></script>
        <script type="text/javascript" src="../js/libs/twitter-bootstrap/js/bootstrap.js?t=080414"></script>
        <script type="text/javascript" src="../js/game.js?t=080414"></script>
        <title>SKIP-BO</title>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-md-1">Game id: <div id="gameId"></div></div>
                <div class="col-md-1">Current player: <div id="currentPlayer"></div></div>
            </div>
            
            <br />
            
            <div class="row">
                <div class="col-md-1"><img id="player1draw" class="card" /></div>
                <div class="col-md-1 col-md-offset-1"><img id="player1hand1" class="card" /></div>
                <div class="col-md-1"><img id="player1hand2" class="card" /></div>
                <div class="col-md-1"><img id="player1hand3" class="card" /></div>
                <div class="col-md-1"><img id="player1hand4" class="card" /></div>
                <div class="col-md-1"><img id="player1hand5" class="card" /></div>
            </div>
            
            <div class="row">
                <div class="col-md-1"><div id="player1drawSize" class="card count"></div></div>
            </div>
            
            <br />
            
            <div class="row">
                <div class="col-md-1"><img id="player1discard1" class="card" /></div>
                <div class="col-md-1"><img id="player1discard2" class="card" /></div>
                <div class="col-md-1"><img id="player1discard3" class="card" /></div>
                <div class="col-md-1"><img id="player1discard4" class="card" /></div>
            </div>
            
            <br />

            <div class="row">
                <div class="col-md-1"><img id="build1" class="card" /></div>
                <div class="col-md-1"><img id="build2" class="card" /></div>
                <div class="col-md-1"><img id="build3" class="card" /></div>
                <div class="col-md-1"><img id="build4" class="card" /></div>
            </div>
            
            <br />
            
            <div class="row">
                <div class="col-md-1"><img id="player2discard1" class="card" /></div>
                <div class="col-md-1"><img id="player2discard2" class="card" /></div>
                <div class="col-md-1"><img id="player2discard3" class="card" /></div>
                <div class="col-md-1"><img id="player2discard4" class="card" /></div>
            </div>
            
            <br />

            <div class="row">
                <div class="col-md-1"><img id="player2draw" class="card" /></div>
                <div class="col-md-1 col-md-offset-1"><img id="player2hand1" class="card" /></div>
                <div class="col-md-1"><img id="player2hand2" class="card" /></div>
                <div class="col-md-1"><img id="player2hand3" class="card" /></div>
                <div class="col-md-1"><img id="player2hand4" class="card" /></div>
                <div class="col-md-1"><img id="player2hand5" class="card" /></div>
            </div>
            
            <div class="row">
                <div class="col-md-1"><div id="player2drawSize" class="card count"></div></div>
            </div>
        </div>
    </body>
</html>
