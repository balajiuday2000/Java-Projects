public class Sender {

    public int base;
    public int bufferNext;
    public int nextSeqNo;
    public Packet[] buffer;

    Sender( int base, int bufferNext, int nextSeqNo, int bufferSize){

        this.base = base;
        this.bufferNext = bufferNext;
        this.nextSeqNo = nextSeqNo;
        buffer = new Packet[bufferSize];
    }
}