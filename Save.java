public class Save implements Command {
    private FileParser Save = new FileParser();

    @Override
    public void Execute() {
        Save.SaveData();
    }
}