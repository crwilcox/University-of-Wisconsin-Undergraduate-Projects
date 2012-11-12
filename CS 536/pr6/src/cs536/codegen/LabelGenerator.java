package cs536.codegen;

public class LabelGenerator
{
    private String prefix;
    private int counter;
    public LabelGenerator(String pfx)
    {
        prefix = pfx;
        counter = 0;
    }

    public String genLabel()
    {
        return ".L" + prefix + "_" + counter++;
    }
}
