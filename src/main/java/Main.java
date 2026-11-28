import javax.swing.SwingUtilities;

public class Main
{
    public static void main(String[] args)
    {
        EpsilonInvestments application = new EpsilonInvestments();
        SwingUtilities.invokeLater(() -> application.start());
    }
}
