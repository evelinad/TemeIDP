TemeIDP
=======

Tema 1 
IDP Arhitectura si interfata proiect

1.Mediul de dezvoltare
2.Implementare
3.Git
   
1.Mediul de dezvoltare
=======================    
    - Limbajul de programare: Java 1.7
    - Mediul de dezvoltare: Eclipse
    

2.Implementare
==============
    2.1 Modulele principale
       * GUI
       * Mediator
       * Net
       * Transfer
       
    2.2 Arhitectura aplicatiei
       
           Clasa Mediator are acces la toate componentele aplicatiei si realizeaza
           legatura dintre acestea. Celelate componente(TransferManager,GUI_Main, SatteManager)
           interactioneaza prin intermediul Mediator(au acces la mediator prin intermdiul campului  med).
           Deoarece se poate face diferenta intre tipurile de operatii de transfer de fisiere
           (send & receive),am implementat patternul State care realizeaza managementul tranzitiei
           intre aceste stari.
           Clasa StateManager gestioneaza aceste stari, clasa ReceiveState corespunde unei operatii de
           download de fisier, clasa SendState corespunde unei operatii de upload de fisier.
           Tranzitiile dintre stari se fac prin intermediul metodelor setreceiveState, setSendState.
           Am utilizat patternul Command pentru gestiunea actiunilor ce se pot aplica
           asupra unui transfer in derulare(start, stop, resume, pause).
           Pentru executarea unei actiuni auspra unu transfer in functie de startea transferului,
           am implementat  State Pattern cu starile StartState, ResumeState, PauseState, StopState.
           Pentru a simula prezenta mai multor useri am definit un fisier de configurare(test.cfg) 
           unde sunt adaugati userii si server porturile asociate.
           Home-ul fiecarui user se defineste in downloads/<username> si contine toate 
           fisierele disponibile pentru transfer.
           
     2.3 GUI
        In aceasta etapa, pentru handler-ele de evenimente am folosit Event Dispatch Thread (EDT).
              
     2.4 Protocolul de comunicatie de transfer de fisiere
           Am implementat protocolul folosind Java nio, transferul de fisiere se face non blocant.
           Fiecare host porneste un thread(modulul net, ServerPeer.java) pe care primeste requesturi.
           Initial un client cere dimensiunea fisiruliu pe care vrea sa il descarce si ulterior 
           interogheaza serverul pentru fiecare fragmnt in parte(fragmentele au dimensiune 4096).
           Un mesaj de transfer al unui fragment contine initial dimensiunea fragmentului de fisier
           (primii 4 bytes)si pe urma continutul.
           Pentru fiecare transfer initiat se porneste cate un thread.

     2.5 Testare
           Pentru validarea functionalitatii legate de transferul de fisiere,
           am folosit unit testing(JUnit).
           Fiecare clasa de testare face un transfer complet, un transfer cu stop start 
           si unul cu pause resume.
           Clasa DiffFiles verifica daca continutul fisierului transferat difera de cel original.
	
	2.6 Clientul Web - modulul webservice
	       Acest client mediaza comunicarea intre apliactie si serviciul web.
           la deschiderea apliactiei, se trimite un mesaj de login prin care se fac publice
           IP-ul,portul serverului local pt transferul de fisiere si fisierele detinute de user.
           Prin intermediul clasei Communicator se obtin informatii regulat de la
           serviciul web despre alti clienti si se  trmite lista actualizata de fisiere.
	
	2.7 Serviciul Web
	       Serviciul Web este implementat folosind Apache Axis si Apache Tomcat.
	       Obiectele transmise catre servicul web sunt trimise ca stringuri ele fiind parsate la destinatie
	       pentru a recrea obiectul.

     2.8 Logging
        Pentru jurnalizarea mesajelor am folosit nivele diferite de granularitate(error pentru exceptii,
        info si debug pentru mesajele obisnuite).
     
        
     2.9 Rulare
        Din Eclipse -> Run As-> Run Configurations -> arguments -> <usernane> <serverport>
        
    
3.Git
======
    Pentru version control, am folosit git.
    Versiunea pentru tema 1 este pe branchul tema1:
    https://github.com/evelinad/TemeIDP/tree/tema1
