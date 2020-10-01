#trade-ohlc-analytics-service

CONTENTS OF THE FILE
---------------------

 * Introduction
 * Requirements
 * Design Details
 * How to Run the Application
 * How to subscribe event/check output
 
 Introduction:
 Application for computing "OHLC" (Open/High/Low/Close) time series based on the 'Trades' input dataset.
 
 Requirement :
 Have multiple threads with Trade Reader , OHLC FSM computation & websocket listener
 1) Trade Reader will read the input file and send packet to OHLC computation engine
 2) OHLC FSM computation engine will compute the OHLC packets based on 15 secs bar
 3) websocket thread will listen to user subscriptions
 
 
 Design Details:
 
 1) Overall Flow:
 Receive user subscriptions from client(Websocket thread) -> input the subscription to subscription queue -> 
 Trade Reader will read subscription from subscription queue -> process the subscription -> 
 Read data from file -> send data to trade queue ->  OHLC FSM computation engine reads data from trade queue and computes OHLC packets
 
 2) DataStructure : Used LinkedBlockingQueue for inter thread communications  
 
 3) class & methods : 
 		OHLCWebSocketHandler : handleTextMessage() - Listens User subscriptions.
 		TradeInputDataReader : readTradeDataForClientSubscriptionStocks(String filePath) - read subscriptions from subscription queue and read trades for the corresponding subscribed stock, sends trade data to OHLC FSM engine
 		OHLCPacketCreator : createOhlcPacket() - create and logs OHLC packets 
 		
 How to Run the Application:
 java -jar trade-ohlc-analytics-service.jar
 
 How to subscribe event/check output: 
 1) start the application using command - java -jar trade-ohlc-analytics-service.jar
 	Note : Placed the trades.json file in resources folder to test easily. But this should be kept outside and reference should be given for the file to read
 2) go to http://localhost:9091 from browser, This will open a html page 
 3) Click on "connect to websocket server" button to connect to websocket
 4) enter subscription message in text box then click on "Send" button. 
 	e.g subscriptiom message - {"event": "subscribe", "symbol": "XXBTZUSD", "interval": 15} 
 	NOTE : Proper validations has not been done. Give the subscription in this format only 
 5) Check the application log for OHLC packets
 