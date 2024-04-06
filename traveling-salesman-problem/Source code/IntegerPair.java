// Buse-Dragomir Alexandru
// utility class used in order to make pairs of Integers
// necessary later in the program

public class IntegerPair
{
    private int first;
    private int second;

    public IntegerPair(int first, int second)
    {
        this.first = first;
        this.second = second;
    }

    public int getFirst()
    {
        return this.first;
    }

    public int getSecond()
    {
        return this.second;
    }

    public void setFirst(int first)
    {
        this.first = first;
    }

    public void setSecond(int second)
    {
        this.second = second;
    }
}
