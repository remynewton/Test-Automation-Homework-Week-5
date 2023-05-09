# Test-Automation-Homework-Week-5

The assignment for the first part of the homework was:
Practical task:
Read text from the file and calculate the numbers of the unique words. Write the result to the file. The main requirement is: using StringUtils and FileUtils to implement it with minimum lines of code.

As such, I modified LogReader.java to accomplish exactly that:
```
package com.laba.solvd.hw;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class LogReader {
    public void countUniqueWords(String logFilePath) {
        File file = new File(logFilePath);
        try {
            String text = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            String[] words = StringUtils.split(text);
            Set<String> uniqueWords = new HashSet<>();
            Collections.addAll(uniqueWords, words);
            int numUniqueWords = uniqueWords.size();
            FileUtils.writeStringToFile(file, "Number of unique words: " + numUniqueWords, StandardCharsets.UTF_8, true);
            System.out.println("Result written to file.");
        } catch (IOException e) {
            System.err.println("Failed to read or write to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
```

I also fixed a number of issues with my code as a result of suggestions from my mentor.
