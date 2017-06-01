Unit / integration / system tests, 3p

För att uppnå en så god slutprodukt som möjligt har arbetsprocessen präglats av tester i olika former. Dels handlar det om avstämning gentemot stakeholders för validering av utvecklingens riktning, men även verifikation för testning av kodningens funktionalitet. De tester som förekommit är enhetstester, systemtester, acceptanstester, integrationstester samt FindBugs.


Enhetstester

De enhetstester som använts har varit av manuell karaktär. Genom att framförallt använda Android Studios Log-funktion har vi kunnat kontrollera att iiCAPTAIN utfört de uppgifter som efterfrågats. Eftersom loggen dock snabbt fylls med mycket information kan det i efterhand konstateras att en mer automatiserad lösning hade varit att föredra. Utvecklingsmiljön som använts erbjuder god funktionalitet för enhetstester och vi kunde därmed istället implementerat en lösning där koden testades automatiskt vid varje körning. På så sätt hade vi underlättat både för oss själva samt för eventuella framtida utvecklare av prototypen.


System-och integrationstestning

Allteftersom iiCAPTAINs grad av komplexitet har stigit har behovet av system- och integrationstestning vuxit. De enskilda beståndsdelar som enhetstestats ska nu kontrolleras i ett bredare, aggregerat perspektiv. Arbetsgången präglades av att Order system (exempelvis iiCAPTAIN) måste testas först så att den skickar rätt data till Inventory system (i detta fallet PortCDM). På så sätt angriper man det totala systemet från rätt riktning då PortCDMs funktionalitet är beroende av att iiCAPTAINs fungerar korrekt. Med denna arbetsmetod bryts applikationen ner i så små beståndsdelar som möjligt där testningen med tid blir allt mer aggregerad.  

Utan hänsyn till inre logik eller kodning ska programvaran även i sin helhet testas så den behärskar olika användarmönster. Då avsedd användning kan skilja sig från faktiskt användning har vi låtit personer utanför arbetsgruppen testa applikationen för att kunna identifiera eventuella buggar. Här testas programvaran på faktorer som stresshantering, användarvänlighet och ad hoc-hantering. Programvaran har även kopplats upp till en gemensam back-end där kommunikationens framgång kunnat kontrolleras. Då applikationens presenterade information hämtas vertikalt från PortCDM har systemtestningen fungerat relativt smärtfritt då misslyckade sändningar av Port Call Messages fångats upp i tidigt skede. Hade en annorlunda utformning applicerats hade en diskrepans kunnat uppstå mellan vad den lokala instansen tror finns på PortCDM från det faktiska innehållet. 

Slutligen har det totala systemet (med samtliga aktörer) testats under genomgång av ett scenario som ska representera processen när ett fartyg lägger till i Göteborg. Här kunde kompatibiliteten mellan de olika applikationerna säkerställas och vissa frågetecken som exempelvis tidszonshantering kunde rätas ut. Genom att gå igenom scenariot några dagar innan det slutgiltiga testet kunde grupperna framgångsrikt identifiera en del buggar i god tid. 


Acceptanstester

Genom möten med aktörer från PortCDM och hamnen på veckobasis har kontinuerliga acceptanstester kunnat appliceras under utvecklingsperioden. Eftersom idéer och funderingar har kunnat utbytas så pass frekvent har eventuella missriktningar kunnat fångas upp relativt tidigt, vilket underlättat utvecklingen markant. Då dessa aktörer tillhör applikationens målgrupp har deras användarupplevelse varit en stor tillgång. 

Något som hade kunnat gynna prototypen är använding av A/B-tester. Genom att utveckla två eller fler prototyper och låta användaren jämföra dem erhåller man då en bättre bild av användarens önskemål. Tyvärr har tiden inte känts tillräcklig för denna typ av test, men det hade varit intressant att se hur applikationens riktning kunnat förändras med hjälp av dem. 

En annan metod som skulle kunnat appliceras för att förbättra slutresultatet är multivariate-tester. Genom att olika kombinationer av utformningen på appens beståndsdelar testas kan man slutligen identifiera den med mest kundvärde. Även detta användes tyvärr ej då vi inte upplevde att det fanns tillräckliga resurser för att hinna förbereda denna sorts tester till mötena. Däremot konstateras i efterhand att Beta-releaser hade inneburit ett lättimplementerad test som samtidigt erbjudit stort värde. Genom att erbjuda aktörer från hamnen tillgång till tidiga releaser hade vi kunnat erhålla djupare feedback då de hade kunnat bekanta sig med applikationen mer än några minuter i veckan. Att appar utanför Google Play är så pass lätta att dela ut och installera i Android-telefoner är ett faktum vi inte utnyttjade tillräckligt.  


FindBugs

Med hjälp av programvaran FindBugs kunde ytterligare buggar identifieras som passerat de tester som genomförts tidigare. Denna process är beskriven ytterligare under dokumentationen för Code Quality.
