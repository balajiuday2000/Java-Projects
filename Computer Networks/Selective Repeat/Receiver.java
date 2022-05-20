public class Receiver {

    public int expectedSeqNo;
    public Packet[] buffer;

    Receiver(int expectedSeqNo, int bufferSize){

        this.expectedSeqNo = expectedSeqNo;
        buffer = new Packet[bufferSize];

    }

    public void emptyBuffer(){

        for(int i = 0; i < buffer.length; i++){
            buffer[i] = null;
        }
    }
}
