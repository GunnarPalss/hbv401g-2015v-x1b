To use the jar file import database_console and everything in the lib folder into your project.

Then put the db folder in your project folder, aka not into the folder that encompasses the src, build and nbproject folder.

Viðmót: Notið database.client.somethingTable.get() til að ná í instance af töflunni sem þið ættlið að nota.

Scraper: Notaið database.client.DatabaseBookScraper.get() til að setja inn nyjar bækur í databaseið, ættuð bara að þurfa create book aðferðina, hún sér um að eyða bókinni ef hún er nú þegar til.

P.S. Við munum setja lýsingar inn um notkun fallanna á næstunni en þau ættu að vera self explanitory.