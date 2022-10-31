package kleicreator.modloader;

import java.io.OutputStream;
import java.io.PrintStream;

public class ModLoaderLogStreamer extends PrintStream {
    public ModLoaderLogStreamer(PrintStream out) {
        super(out);
    }

    @Override
    public void print(String s) {
        super.print(s);
        ModLoader.modEditor.getLogOutput().setText(s);
    }
}
