
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author stefano.mureddu
 */
public class PortFilter extends DocumentFilter{
    
    @Override
    public void insertString(DocumentFilter.FilterBypass fb, int offset,
            String text, AttributeSet attr)
            throws BadLocationException {
        StringBuilder buffer = new StringBuilder(text.length());
        for (int i = buffer.length() - 1; i >= 0; i--) {
            char ch = buffer.charAt(i);
            char[] chars = {0x48,0x49,0x50,0x51,0x52,0x53,0x54,0x55,0x56,0x57};
            for(int j = 0; j<chars.length;j++){
                if (ch == chars[j]) {
                buffer.append(ch);
                }   
            }
            buffer.append(ch);
        }
        super.insertString(fb, offset, buffer.toString(), attr);
    }

    @Override
    public void replace(DocumentFilter.FilterBypass fb,
            int offset, int length, String string, AttributeSet attr) throws BadLocationException {
        if (length > 0) {
        fb.remove(offset, length);
        }
        insertString(fb, offset, string, attr);
    }
}
