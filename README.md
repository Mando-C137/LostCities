# Anleitung zur Bedienung der Software

Hallo! In diesem Ordner ist die Software aufzufinden.

## Agenten gegeneinander

Für Sie relevant sind hierfür nur 2 Klassen.

Die Klasse <code> domain.demo.Demogames.java </code>  dient zum Testen der Agenten untereinander. Dabei kann konfiguriert werden, welche Agenten gegeneinander antreten sollen: 

<code> 		
		Informationset.setParameter(1);
					
	CheatNode.setParameter(1);
	
    Game g = Game.twoRandoms();

    g = Game.ISMCTSvsRANDOM();

    // g = Game.ISMCTSvsSIMPLE();

    // g = Game.ISMCTSvsPIMC();

    // g = Game.PIMCvsRandom();

    // g = Game.PIMCvsSIMPLE();

    // g = Game.SIMPLEvsRandom();
</code>

Wie hier dargestellt, muss für die Konfiguration die entsprechende Zeile ein- bzw. die anderen auskommentiert werden. Zustätzlich kann der Parameter <code> C </code> der UCT-Formel gesetzt werden. 

Nach Ausführen der main-Methode wird ein Spiel ausgeführt, dabei spielen die Agente abwechselnd gegeneinander. Der Spielstand, die Handkarten, die ausgewählten Aktionen werden dabei in der Kommandozeile ausgegeben.

## Agenten vs Mensch 

Hierfür ist es nötig, die <code>javafx</code>-libray zu installieren und in den BuildPath miteinbeziehen.

Relevant hierfür ist die Klasse <code>userinterface.Main.java</code>. 

Analog zu der oben genannten Konfiguration können Sie hier den gegnerischen Agenten einstellen: 

<code>

	Game game;

    game = Game.SimpleVsMe();

    // game = Game.ISMCTSvsME();

    // game = Game.CHEATvsME();

    // game = game.RandomvsMe();

    // game = PimcStrategy.PIMCvsHuman();</code>

Anschließend muss nur die main-Methode ausgeführt werden und die GUI startet.

Durch das Klicken auf ''opponent'' spielt der Gegner den ersten Zug. Danach ist der Spielablauf ziemlich selbsterklärend. 

Wenn gegen einen MCTS-Agenten gespielt wird, dann braucht dieser bis zu zehn Sekunden Zeit, um sich für eine Aktion zu entscheiden. Währenddessen freezet die GUI und ist unresponsive, was aber keine Probleme machen sollte!

## aktuelle Konfiguration der MCTS-Agenten

Die MCTS-Agenten nutzen als Simulationsstrategie den Regelbasierten Agenten SIMPLE AI.

Der Cheating MCTS-Agent und der IS-MCTS-Agent benötigt 10 Sekunden Zeit für seinen Zug. 
Der PIMC-Agent erstellt aktuell 30 Determinisierungen und löst diese via Cheating-MCTS, die alle 8 Sekunden lang dauern. 