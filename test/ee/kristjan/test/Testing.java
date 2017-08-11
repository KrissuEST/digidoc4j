package ee.kristjan.test;

//import static org.digidoc4j.Configuration.Mode.TEST;
//import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

import org.apache.commons.io.FileUtils;
//import org.digidoc4j.Configuration;
//import org.digidoc4j.TSLCertificateSource;
//import org.digidoc4j.impl.bdoc.tsl.TslLoader;
import org.junit.Test;

public class Testing {
  
  // Kristjan's test without digidoc4j library.
  @SuppressWarnings("ConstantConditions")
  @Test
  public void clearTSLCache() throws Exception {
    
    String fromFile = "https://www.w3schools.com/xml/note.xml";
    String toFile1 = "C:\\Users\\Kristjani-PC\\git\\digidoc4j\\testFiles\\kristjanTestFiles1\\newFile1.xml";
    String toFile2 = "C:\\Users\\Kristjani-PC\\git\\digidoc4j\\testFiles\\kristjanTestFiles2\\newFile2.xml";
    
    // 1) Asking somewhere (in library a query is made from web) a file X and saving it to directory D.
    try {
        //connectionTimeout, readTimeout = 10 seconds
        FileUtils.copyURLToFile(new URL(fromFile), new File(toFile1), 10000, 10000);
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    // 2) Taking file X from directory D and asking it's file creation time T1.
    Path link = Paths.get("C:\\Users\\Kristjani-PC\\git\\digidoc4j\\testFiles\\kristjanTestFiles1\\newFile1.xml");
    BasicFileAttributes attrLink = Files.readAttributes(link, BasicFileAttributes.class);
    
    FileTime fileTime1 = attrLink.creationTime();
    System.out.println("Time1 value is " + fileTime1);
    System.out.println("");
    
    // 3) Deleting file X (in library just deleting directory D content).
    String directory = "C:\\Users\\Kristjani-PC\\git\\digidoc4j\\testFiles\\kristjanTestFiles1";
    FileUtils.cleanDirectory(new File(directory));
    
    // 4) Asking somewhere (in library a query is made from web) a file X and saving it to directory D.
    try {
      //connectionTimeout, readTimeout = 10 seconds
      FileUtils.copyURLToFile(new URL(fromFile), new File(toFile2), 10000, 10000);
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    // 5) Taking again file X from directory D and asking it's file creation time T2.
    Path link2 = Paths.get("C:\\Users\\Kristjani-PC\\git\\digidoc4j\\testFiles\\kristjanTestFiles2\\newFile2.xml");
    BasicFileAttributes attrLink2 = Files.readAttributes(link2, BasicFileAttributes.class);
    
    FileTime fileTime2 = attrLink2.creationTime();
    System.out.println("Time2 value is " + fileTime2);
    System.out.println("");
    
    // 6) Deleting file X (in library just deleting directory D content).
    String directory2 = "C:\\Users\\Kristjani-PC\\git\\digidoc4j\\testFiles\\kristjanTestFiles2";
    FileUtils.cleanDirectory(new File(directory2));
    
    // 7) Checking if T2 > T1 (in our test is not, although logically it should be).
    if (fileTime2.compareTo(fileTime1) == 0) {
       System.out.println(fileTime2.compareTo(fileTime1) + " times are equal");
    } else if (fileTime2.compareTo(fileTime1) > 0) {
       System.out.println(fileTime2.compareTo(fileTime1) + " fileTime2 is bigger");
    } else {
       System.out.println(fileTime2.compareTo(fileTime1) + " fileTime2 is smaller");
    }
    
  }
  
}
