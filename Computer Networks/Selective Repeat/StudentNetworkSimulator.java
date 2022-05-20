public class StudentNetworkSimulator extends NetworkSimulator
{
    /*     *   int B           : a predefined      *

     * Predefined Constants (static member variables):
     *
     *   int MAXDATASIZE : the maximum size of the Message data and
     *                     Packet payload
     *
     *   int A           : a predefined integer that represents entity A
integer that represents entity B
     *
     * Predefined Member Methods:
     *  void stopTimer(int entity):
     *       Stops the timer running at "entity" [A or B]
     *  void startTimer(int entity, double increment):
     *       Starts a timer running at "entity" [A or B], which will expire in
     *       "increment" time units, causing the interrupt handler to be
     *       called.  You should only call this with A.
     *  void toLayer3(int callingEntity, Packet p)
     *       Puts the packet "p" into the network from "callingEntity" [A or B]
     *  void toLayer5(String dataSent)
     *       Passes "dataSent" up to layer 5
     *  double getTime()
     *       Returns the current time in the simulator.  Might be useful for
     *       debugging.
     *  int getTraceLevel()
     *       Returns TraceLevel
     *  void printEventList()
     *       Prints the current event list to stdout.  Might be useful for
     *       debugging, but probably not.
     *
     *
     *  Predefined Classes:
     *
     *  Message: Used to encapsulate a message coming from layer 5
     *    Constructor:
     *      Message(String inputData):
     *          creates a new Message containing "inputData"
     *    Methods:
     *      boolean setData(String inputData):
     *          sets an existing Message's data to "inputData"
     *          returns true on success, false otherwise
     *      String getData():
     *          returns the data contained in the message
     *  Packet: Used to encapsulate a packet
     *    Constructors:
     *      Packet (Packet p):
     *          creates a new Packet that is a copy of "p"
     *      Packet (int seq, int ack, int check, String newPayload)
     *          creates a new Packet with a sequence field of "seq", an
     *          ack field of "ack", a checksum field of "check", and a
     *          payload of "newPayload"
     *      Packet (int seq, int ack, int check)
     *          create a new Packet with a sequence field of "seq", an
     *          ack field of "ack", a checksum field of "check", and
     *          an empty payload
     *    Methods:
     *      boolean setSeqnum(int n)
     *          sets the Packet's sequence field to "n"
     *          returns true on success, false otherwise
     *      boolean setAcknum(int n)
     *          sets the Packet's ack field to "n"
     *          returns true on success, false otherwise
     *      boolean setChecksum(int n)
     *          sets the Packet's checksum to "n"
     *          returns true on success, false otherwise
     *      boolean setPayload(String newPayload)
     *          sets the Packet's payload to "newPayload"
     *          returns true on success, false otherwise
     *      int getSeqnum()
     *          returns the contents of the Packet's sequence field
     *      int getAcknum()
     *          returns the contents of the Packet's ack field
     *      int getChecksum()
     *          returns the checksum of the Packet
     *      int getPayload()
     *          returns the Packet's payload
     *
     */

    /*   Please use the following variables in your routines.
     *   int WindowSize  : the window size
     *   double RxmtInterval   : the retransmission timeout
     *   int LimitSeqNo  : when sequence number reaches this value, it wraps around
     */

    public static final int FirstSeqNo = 0;
    private int WindowSize;
    private double RxmtInterval;
    private int LimitSeqNo;

    // Add any necessary class variables here.  Remember, you cannot use
    // these variables to send messages error free!  They can only hold
    // state information for A or B.
    // Also add any necessary methods (e.g. checksum of a String)

    //Variables for statistics
    public int originalMessages = 0;
    public int corruptedPackets = 0;
    public int retransmittedPackets = 0;
    public int noOfACKs  = 0;
    public int corruptedACKs = 0;
    public int messagesDelivered = 0;

    public double[] RTTs = new double[10000];
    public int[] isRetransmitted = new int[10000];
    public int[] isACKed = new int[10000];



    //Function to calculate the checksum of packets
    public int checksumPacket(int seqNo, int ackNo, String message){

        int checksum = 0;
        checksum += seqNo;
        checksum += ackNo;
         for(char c : message.toCharArray()){
             int value = c;
             checksum += value;
         }
         return checksum;
    }

    //Function to calculate the checksum of ACK packets
    public int checksumACK(int seqNo, int ackNo){

        int checksum = 0;
        checksum += seqNo;
        checksum += ackNo;

        return checksum;
    }

    // This is the constructor.  Don't touch!
    public StudentNetworkSimulator(int numMessages,
                                   double loss,
                                   double corrupt,
                                   double avgDelay,
                                   int trace,
                                   int seed,
                                   int winsize,
                                   double delay)
    {
        super(numMessages, loss, corrupt, avgDelay, trace, seed);
        WindowSize = winsize;
        LimitSeqNo = winsize*2; // set appropriately; assumes SR here!
        RxmtInterval = delay;
    }

    //Sender object is created
    Sender sender = new Sender(FirstSeqNo, FirstSeqNo, FirstSeqNo, 10000);
    //Receiver object is created
    Receiver receiver = new Receiver(FirstSeqNo,10000);


    // This routine will be called whenever the upper layer at the sender [A]
    // has a message to send.  It is the job of your protocol to ensure that
    // the data in such a message is delivered in-order, and correctly, to
    // the receiving upper layer.
    protected void aOutput(Message message)
    {
        System.out.println("[aOutput] Message received from layer 5 : " + message.getData());
        originalMessages += 1; //Keeps count of the number of messages received from layer 5
        int seqNo = sender.nextSeqNo;
        int ackNo = -1;
        int checksum = checksumPacket(seqNo,ackNo,message.getData());
        Packet packet = new Packet(seqNo, ackNo, checksum, message.getData()); //Packet is created
        System.out.println("[aOutput] Buffering packet : " + (packet.getSeqnum() % LimitSeqNo) );
        sender.buffer[sender.bufferNext] = packet; //Packet is buffered
        sender.bufferNext += 1; //To get to the next free position in the buffer

        if((packet.getSeqnum() >= sender.base) && packet.getSeqnum() < (sender.base + WindowSize)){ //To check if packet falls within the window

            System.out.println("[aOutput] Sending packet : " + (packet.getSeqnum() % LimitSeqNo));
            toLayer3(A, packet); //Packet is sent
            RTTs[packet.getSeqnum()] = getTime(); //The start time of each packet is recorded

            if(packet.getSeqnum() == sender.base){
                System.out.println("[aOutput] Timer is started !");
                startTimer(A, RxmtInterval);
            }

            sender.nextSeqNo += 1; //Sequence number of the packets is updated
        }
    }

    // This routine will be called whenever a packet sent from the B-side
    // (i.e. as a result of a toLayer3() being done by a B-side procedure)
    // arrives at the A-side.  "packet" is the (possibly corrupted) packet
    // sent from the B-side.
    protected void aInput(Packet packet)
    {
        if(packet.getChecksum() != checksumPacket(packet.getSeqnum(), packet.getAcknum(), packet.getPayload())){ //To handle corrupt ACKs

            System.out.println("[aInput] ACK is corrupted. Drop !");
            corruptedACKs += 1; //Keeps track of the number of corrupted ACKs
            return;
        }

        else if(packet.getAcknum() < sender.base){ //To handle duplicate ACKs

            System.out.println("[aInput] Duplicate ACK received. Drop !");
            return;
        }

        System.out.println("[aInput] Received Cumulative ACK : " + (packet.getAcknum() % LimitSeqNo));
        RTTs[packet.getAcknum()] = getTime() - RTTs[packet.getAcknum()]; //Start time is subtracted from end time to get RTT
        isACKed[packet.getAcknum()] = 1;

        for(int i = packet.getAcknum(); i >= 0; i--){
            if(isACKed[i] == 0){
                RTTs[i] = getTime() - RTTs[i];
                isACKed[i] = 1;
            }
        }
        sender.base = packet.getAcknum() + 1; //The first unacknowledged packet is now set to the one after the cumulatively acknowledged packet

        if(sender.base == sender.nextSeqNo){

            stopTimer(A);
            System.out.println("[aInput] Timer is stopped !");
            System.out.println();
        }

        else{
            stopTimer(A);
            startTimer(A,RxmtInterval);
            System.out.println("[aInput] Timer is restarted !");
        }
    }

    // This routine will be called when A's timer expires (thus generating a
    // timer interrupt). You'll probably want to use this routine to control
    // the retransmission of packets. See startTimer() and stopTimer(), above,
    // for how the timer is started and stopped.
    protected void aTimerInterrupt()
    {
        System.out.println("[aTimerInterrupt] Time out for packet : " + (sender.buffer[sender.base].getSeqnum() % LimitSeqNo));
        Packet packet = sender.buffer[sender.base];
        System.out.println("[aTimerInterrupt] Resending packet : " + (sender.buffer[sender.base].getSeqnum() % LimitSeqNo));
        toLayer3(A, packet); //Only the first unacknowledged packet is resent
        retransmittedPackets += 1; //Keeps track of the number of retransmitted packets
        isRetransmitted[sender.buffer[sender.base].getSeqnum()] = 1; //To know which packets have been retransmitted
        startTimer(A, RxmtInterval);
        System.out.println("[aTimerInterrupt] Timer is started !");
    }

    // This routine will be called once, before any of your other A-side
    // routines are called. It can be used to do any required
    // initialization (e.g. of member variables you add to control the state
    // of entity A).
    protected void aInit()
    {
        System.out.println();
        System.out.println("[aInit] Sender is initialized !");
        for(int i = 0; i < isRetransmitted.length; i++){
            isRetransmitted[i] = 0; //Initially set to 0 indicating none of the packets are retransmitted
        }
        for(int i = 0; i < isACKed.length; i++){
            isACKed[i] = 0; //Initially set to 0 indicating that RTT has not been calculated for none of the packets
        }
    }

    // This routine will be called whenever a packet sent from the A-side
    // (i.e. as a result of a toLayer3() being done by an A-side procedure)
    // arrives at the B-side.  "packet" is the (possibly corrupted) packet
    // sent from the A-side.
    protected void bInput(Packet packet)
    {
        if(packet.getChecksum() != checksumPacket(packet.getSeqnum(), packet.getAcknum(), packet.getPayload())){ //To handle corrupt packets

            System.out.println("[bInput] Packet is corrupted. Drop !");
            corruptedPackets += 1;
        }

        else if(packet.getSeqnum() < receiver.expectedSeqNo){ //To handle duplicate packets

            System.out.println("[bInput] Sending duplicate ACK for packet : " + (packet.getSeqnum() % LimitSeqNo));
            int seqNo = packet.getSeqnum();
            int ackNo = packet.getSeqnum();
            int checksum = checksumACK(seqNo,ackNo);
            Packet ack = new Packet(seqNo, ackNo, checksum);
            toLayer3(B, ack);
            noOfACKs += 1; //Keeps track of the number of ACKs sent
        }

        else if (packet.getSeqnum() > receiver.expectedSeqNo && packet.getSeqnum() < receiver.expectedSeqNo + WindowSize){ //To handle out-of-order packets

            System.out.println("[bInput] Received out-of-order packet : " + (packet.getSeqnum() % LimitSeqNo));
            System.out.println("[bInput] Buffering out-of-order packet : " + (packet.getSeqnum() % LimitSeqNo));
            receiver.buffer[packet.getSeqnum()] = packet; //The out-of-order packets are buffered

        }

        else if(packet.getSeqnum() == receiver.expectedSeqNo){ //To handle in-order-packets

            System.out.println("[bInput] Received in-order packet : " + (packet.getSeqnum() % LimitSeqNo));
            System.out.println("[bInput] Message sent to layer 5 : " + packet.getPayload());
            toLayer5(packet.getPayload()); //Message is delivered to layer 5
            messagesDelivered += 1; //Keeps track of the number of messages delivered

            for(int i = 0; i < receiver.buffer.length; i++){ // To deliver all buffered messages

                if(receiver.buffer[i] != null){
                    System.out.println("[bInput] Message sent to layer 5 : " + receiver.buffer[i].getPayload());
                    toLayer5(receiver.buffer[i].getPayload());
                    receiver.expectedSeqNo += 1;
                    messagesDelivered += 1;
                    packet = receiver.buffer[i];
                }
            }

            receiver.emptyBuffer(); //To empty the buffer once all buffered messages are delivered

            int seqNo = packet.getSeqnum();
            int ackNo = packet.getSeqnum();
            int checksum = checksumACK(seqNo,ackNo);
            Packet ack = new Packet(seqNo, ackNo, checksum);

            System.out.println("[bInput] Sending cumulative ACK for packet : " + (ack.getAcknum() % LimitSeqNo));
            toLayer3(B, ack); //Cumulative ACK is sent
            noOfACKs += 1; //Keeps track of the number of ACKs sent
            receiver.expectedSeqNo += 1; //Updates the sequence number to be expected by the receiver
        }
    }

    // This routine will be called once, before any of your other B-side
    // routines are called. It can be used to do any required
    // initialization (e.g. of member variables you add to control the state
    // of entity B).
    protected void bInit()
    {
        System.out.println("[bInit] Receiver is initialized !");
        System.out.println();
        for(int i = 0; i < receiver.buffer.length; i++){
            receiver.buffer[i] = null; //Initializes the buffer
        }
    }

    // Used to print final statistics

    double lostRatio(){ //To calculate the ratio of lost packets

        double r = 1.0 * retransmittedPackets;
        double c = 1.0 * corruptedPackets;
        double o = 1.0 * originalMessages;
        double a = 1.0 * noOfACKs;
        double numerator = r - c;
        double denominator = (o + r) + a;

        return (numerator/denominator);
    }

    double corruptRatio(){ //To calculate the ratio of corrupted packets

        double r = 1.0 * retransmittedPackets;
        double c = 1.0 * corruptedPackets;
        double o = 1.0 * originalMessages;
        double a = 1.0 * noOfACKs;
        double numerator = c;
        double denominator = (o + r) + a - (r - c);

        return (numerator/denominator);
    }

    double avgRTT() //Calculates average RTT.
    {
        double avgRTT = 0;
        for(int i = 0; i < originalMessages; i++)
        {
            if(isRetransmitted[i] == 0)
            {
                avgRTT = avgRTT + RTTs[i];
            }
        }
        avgRTT = avgRTT / originalMessages;

        return avgRTT;
    }

    double avgCommunicationTime() //Calculates average communication time.
    {
        double avgCommTime = 0;
        for(int i = 0; i < originalMessages; i++)
        {
            avgCommTime = avgCommTime + RTTs[i];
        }
        avgCommTime = avgCommTime / originalMessages;

        return avgCommTime;
    }

    protected void Simulation_done()
    {
        // TO PRINT THE STATISTICS, FILL IN THE DETAILS BY PUTTING VARIABLE NAMES. DO NOT CHANGE THE FORMAT OF PRINTED OUTPUT
        System.out.println("\n\n===============STATISTICS=======================");
        System.out.println("Number of original packets transmitted by A : " + originalMessages );
        System.out.println("Number of retransmissions by A : " + retransmittedPackets);
        System.out.println("Number of data packets delivered to layer 5 at B : " + messagesDelivered);
        System.out.println("Number of ACK packets sent by B : " + noOfACKs);
        System.out.println("Number of corrupted packets : " + corruptedPackets);
        System.out.println("Ratio of lost packets : " + lostRatio() );
        System.out.println("Ratio of corrupted packets : " + corruptRatio());
        System.out.println("Average RTT : " + avgRTT());
        System.out.println("Average communication time : " + avgCommunicationTime());
        System.out.println("==================================================");

        // PRINT YOUR OWN STATISTIC HERE TO CHECK THE CORRECTNESS OF YOUR PROGRAM
        System.out.println("\nEXTRA:");
        System.out.println("Number of corrupt ACK packets received by A : " + corruptedACKs);
    }
}