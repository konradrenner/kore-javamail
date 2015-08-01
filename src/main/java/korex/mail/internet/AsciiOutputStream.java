/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package korex.mail.internet;

import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;

/**
 * An OutputStream that determines whether the data written to it is all ASCII,
 * mostly ASCII, or mostly non-ASCII.
 */
public class AsciiOutputStream extends OutputStream {

    private boolean breakOnNonAscii;
    private int ascii = 0, non_ascii = 0;
    private int linelen = 0;
    private boolean longLine = false;
    private boolean badEOL = false;
    private boolean checkEOL = false;
    private int lastb = 0;
    private int ret = 0;

    public AsciiOutputStream(boolean breakOnNonAscii, boolean encodeEolStrict) {
        this.breakOnNonAscii = breakOnNonAscii;
        checkEOL = encodeEolStrict && breakOnNonAscii;
    }

    public void disableBreakOnNonAscii() {
        breakOnNonAscii = false;
    }

    public void write(int b) throws IOException {
        check(b);
    }

    public void write(byte b[]) throws IOException {
        write(b, 0, b.length);
    }

    public void write(byte b[], int off, int len) throws IOException {
        len += off;
        for (int i = off; i < len; i++) {
            check(b[i]);
        }
    }

    private final void check(int b) throws IOException {
        b &= 0xff;
        if (checkEOL
                && ((lastb == '\r' && b != '\n') || (lastb != '\r' && b == '\n'))) {
            badEOL = true;
        }
        if (b == '\r' || b == '\n') {
            linelen = 0;
        } else {
            linelen++;
            if (linelen > 998) // 1000 - CRLF
            {
                longLine = true;
            }
        }
        if (MimeUtility.nonascii(b)) { // non-ascii
            non_ascii++;
            if (breakOnNonAscii) {	// we are done
                ret = MimeUtility.MOSTLY_NONASCII;
                throw new EOFException();
            }
        } else {
            ascii++;
        }
        lastb = b;
    }

    /**
     * Return ASCII-ness of data stream.
     */
    public int getAscii() {
        if (ret != 0) {
            return ret;
        }
	// If we're looking at non-text data, and we saw CR without LF
        // or vice versa, consider this mostly non-ASCII so that it
        // will be base64 encoded (since the quoted-printable encoder
        // doesn't encode this case properly).
        if (badEOL) {
            return MimeUtility.MOSTLY_NONASCII;
        } else if (non_ascii == 0) { // no non-us-ascii characters so far
            // if we've seen a long line, we degrade to mostly ascii
            if (longLine) {
                return MimeUtility.MOSTLY_ASCII;
            } else {
                return MimeUtility.ALL_ASCII;
            }
        }
        if (ascii > non_ascii) // mostly ascii
        {
            return MimeUtility.MOSTLY_ASCII;
        }
        return MimeUtility.MOSTLY_NONASCII;
    }
}
