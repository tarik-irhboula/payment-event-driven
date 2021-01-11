# e-payement
Kata SFEIR E paiement


****

- Un provider fait une demande de paiement pour un client. 
- Le client à partir d’une notification, a le choix d’accepter ou refuser la demande. 
- Si demande acceptée, la demande est traitée. 
- Si demande refusée, renvoie erreur et demande et transaction non effectuée. 
- Si demande traitée, Notifier le professionnel et particulier

Acteurs : 
- Provider
- Client
- Compte utilisateur
- Demande de paiement
- Transaction 



User stories: 


US1: 
    En tant que provider P1, Je dois pouvoir envoyer une demande de paiement vers un client C1 avec les informations suivantes : 
    - Montant à payer
    - Identifiant du particulier
    - Titre de paiement
    Pour payer un achat. 

US2:
    En tant que client, Je dois pouvoir recevoir une notification pour accepter ou refuser une demande de paiement. 

US3:
    En tant que client, Je ne peux pas accepter une demande de paiement reçu, si le montant de paiement est supérieur au solde de mon compte. 

US4:
    En tant que client, Je dois pouvoir accepter la demande  de paiement si mon solde est supérieur ou égale au montant de la demande.

US5:
    En tant que client, je dois pouvoir refuser la demande de paiement

US6: 
    En tant que provider, je dois recevoir une notification d'acceptation et de rejet d'une demande de paiement que j'ai initié

US7: 
    En tant que client, je dois avoir la main pour alimenter mon compte avec un code.

    
    
Methodes :  

    PaymentsService :
        fun requestPayment(amount, clientId, providerId, title) : requestPaymentCreated
        fun acceptPayment(requestId):paymentRequestAccepted
        fun refusePayment(requestId):paymentRefused

    AccountsService:
        fun supplyAccount(code):accountSupplied
        fun getSoldeAccount(accountId):value
    MoneyTokensService:
        fun genereteToken(amount):token
        

Event Handlers : 

    NotificationService:
        onPaymentRequestCreated(event):clientNotified
        onPaymentAccepted(event):providerNotified
        onPaymentRefused(event):providerNotified
    AccountsService:
        onPaymentRequestAccepted(event): transactionProcessed/transactionRefused
    PaymentService:
        onTransactionProcessed():paymentAccepted
        onTransactionRefused():paymentRefused
    MoneyTokenService:
        onTokenValueRequest(code):tokenValue

Events : 
    
    


Services : 
PaymentsService
NotificationsService
AccountsService
MoneyTokensService

