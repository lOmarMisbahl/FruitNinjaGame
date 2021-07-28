public class Load implements Command {
    private FileParser Load = new FileParser();

    @Override
    public void Execute() {
        Load.LoadData();
    }
}