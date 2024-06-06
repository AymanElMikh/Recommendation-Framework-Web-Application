package recommendation.films.filmflix.utils.duke;

import java.util.ArrayList;
import java.util.Arrays;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.Reader;
import java.io.StringReader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;


public class FileResource {
    private String myPath;
    private String mySource;
    private File mySaveFile;

    public FileResource () {
        initRead();
    }


    public FileResource (File file) {
        initRead(file);
    }
    public FileResource (String filename) {
        initRead(filename);
    }

    public FileResource (boolean writable) {
        if (writable) {
            initWrite();
        }
        else {
            initRead();
        }
    }

    public FileResource (File file, boolean writable) {
        if (writable) {
            initWrite(file);
        }
        else {
            initRead(file);
        }
    }


    public FileResource (String filename, boolean writable) {
        if (writable) {
            initWrite(filename);
        }
        else {
            initRead(filename);
        }
    }

    public Iterable<String> lines () {
        return new TextIterable(mySource, "\\n");
    }

    public Iterable<String> words () {
        return new TextIterable(mySource, "\\s+");
    }

    public String asString () {
        return mySource;
    }
    public CSVParser getCSVParser () {
        return getCSVParser(true);
    }


    public CSVParser getCSVParser (boolean withHeader) {
        return getCSVParser(withHeader, ",");
    }

    public CSVParser getCSVParser (boolean withHeader, String delimiter) {
        if (delimiter == null || delimiter.length() != 1) {
            throw new ResourceException("FileResource: CSV delimiter must be a single character: " + delimiter);
        }
        try {
            char delim = delimiter.charAt(0);
            Reader input = new StringReader(mySource);
            if (withHeader) {
                return new CSVParser(input, CSVFormat.EXCEL.withHeader().withDelimiter(delim));
            }
            else {
                return new CSVParser(input, CSVFormat.EXCEL.withDelimiter(delim));
            }
        }
        catch (Exception e) {
            throw new ResourceException("FileResource: cannot read " + myPath + " as a CSV file.");
        }
    }

    public Iterable<String> getCSVHeaders (CSVParser parser) {
        return parser.getHeaderMap().keySet();
    }
    public void write (String s) {
        ArrayList<String> list = new ArrayList<>();
        list.add(s);
        write(list);
    }

    /**
     * Writes a list of strings to the end of this file, one element per line.
     * 
     * @param list the strings to saved to the file
     */
    public void write (StorageResource list) {
        // we know it is an ArrayList underneath
        write((ArrayList<String>)(list.data()));
    }

    public void write (String[] list) {
        // BUGBUG: yuck :(
        write(new ArrayList<String>(Arrays.asList(list)));
    }


    public void write (ArrayList<String> list) {
        if (mySaveFile != null) {
            // build string to save
            StringBuilder sb = new StringBuilder();
            for (String s : list) {
                sb.append(s);
                sb.append("\n");
            }
            // save it locally (so it can be read later)
            mySource += sb.toString();
            // save it externally to the file
            try (PrintWriter writer = new PrintWriter(new FileWriter(mySaveFile, true))) {
                writer.println(sb.toString());
            }
            catch (Exception e) {
                throw new ResourceException("FileResource: cannot change " + mySaveFile);
            }
        }
    }

    // Prompt user for file to open
    private void initRead () {
        File f = FileSelector.selectFile();
        if (f == null) {
            throw new ResourceException("FileResource: no file choosen for reading");
        }
        else {
            initRead(f);
        }
    }

    // Create from a given File
    private void initRead (File f) {
        try {
            initRead(f.getCanonicalPath());
        }
        catch (Exception e) {
            throw new ResourceException("FileResource: cannot access " + f);
        }
    }

    // Create from the name of a File
    private void initRead(String filePathOrURL) {
        try {
            // Update the myPath variable to hold the file path or URL
            myPath = filePathOrURL;

            InputStream is;
            // Check if the provided filePathOrURL is a URL or a local file path
            if (filePathOrURL.startsWith("http://") || filePathOrURL.startsWith("https://")) {
                // If it's a URL, open an input stream from the URL
                URL url = new URL(filePathOrURL);
                is = url.openStream();
            } else {
                // If it's a local file path, open an input stream from the file
                is = new FileInputStream(filePathOrURL);
            }

            // Read the contents of the file or URL into mySource
            mySource = initFromStream(is);
        } catch (Exception e) {
            // Handle any exceptions by throwing a ResourceException
            throw new ResourceException("FileResource: cannot access " + filePathOrURL, e);
        }
    }

    // store data (keep in sync with URLResource)
    private String initFromStream (InputStream stream) {
        try (BufferedReader buff = new BufferedReader(new InputStreamReader(stream, "UTF-8"))) {
            StringBuilder contents = new StringBuilder();
            String line = null;
            while ((line = buff.readLine()) != null) {
                contents.append(line + "\n");
            }
            return contents.toString();
        }
        catch (Exception e) {
            throw new ResourceException("FileResource: error encountered reading " + myPath, e);
        }
    }

    // prompt user for file for writing
    private void initWrite () {
        File f = FileSelector.saveFile();
        if (f == null) {
            throw new ResourceException("FileResource: no file choosen for writing");
        }
        else {
            initWrite(f);
        }
    }

    // create file for writing
    private void initWrite (File f) {
        try {
            mySaveFile = f;
            if (f.exists() && f.canWrite()) {
                initRead(f);
            }
            else {
                mySource = "";
                myPath = f.getCanonicalPath();
            }
        }
        catch (Exception e) {
            throw new ResourceException("FileResource: cannot access " + f, e);
        }
    }

    // create file for writing
    private void initWrite (String fname) {
        try {
            URL loc = getClass().getClassLoader().getResource(fname);
            if (loc != null) {
                fname = loc.toString();
            }
            initWrite(new File(fname));
        }
        catch (Exception e) {
            throw new ResourceException("FileResource: cannot access " + fname);
        }
    }
}
