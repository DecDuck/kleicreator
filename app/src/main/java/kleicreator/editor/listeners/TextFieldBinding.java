package kleicreator.editor.listeners;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class TextFieldBinding {
    private JTextComponent field;
    private Function target;

    public TextFieldBinding(JTextComponent field, Function target) {
        this.field = field;
        this.target = target;

        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                Set();
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                Set();
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                Set();
            }

            public void Set(){
                target.set(field.getText());
            }
        });
    }

    public interface Function {
        void set(String value);
    }

}
