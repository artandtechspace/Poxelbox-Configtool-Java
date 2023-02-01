# Poxelbox-Configtool-Java
Das Poxelbox-Configtool (Java edition) ist Zusatzsoftware für die [Poxelbox] (https://github.com/artandtechspace/Poxelbox) zum einfacheren bearbeiten der Config der Kontrollunit.

# How to run
Lade die .jar datei unter Releases runter und führe diese aus.
Gib die IP-Adresse der Kontrollunit mit port ein und drücke 'confirm'.
Wenn die Kontrollunit nicht auf einen Windows-Betriebsystem läuft, wird ihre aktuelle Kunfiguration geladen und im UI des Poxelbox-Configtool angezeigt.
Diese kann nun bearbeitet werden.
Die bearbeitete Kunfiguration wird mit dem betätigen von 'Save Settings' zu der Kontrollunit gesendet, welche diese speichert und sich selbst neustartet.
Wenn 'Reload Settings' gedrückt wird wird erneut eine Anfrage für die aktuelle Kunfiguration an die Kontrollunit gesendet.
