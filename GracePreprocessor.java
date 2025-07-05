import java.io.*;
import java.util.*;

public class GracePreprocessor {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Usage: java GracePreprocessor <input.grace>");
            System.exit(1);
        }
        
        String inputFile = args[0];
        String outputFile = inputFile.replace(".grace", "_processed.grace");
        
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        PrintWriter writer = new PrintWriter(new FileWriter(outputFile));
        
        String line;
        int lastIndent = 0;
        Stack<Integer> indentStack = new Stack<>();
        indentStack.push(0);
        
        while ((line = reader.readLine()) != null) {
            String trimmed = line.trim();
            
            // Skip empty lines and comments
            if (trimmed.isEmpty() || trimmed.startsWith("$")) {
                writer.println(line);
                continue;
            }
            
            int indent = line.length() - line.ltrim().length();
            
            // Handle indentation changes
            if (indent > lastIndent) {
                // Increase indentation - add opening brace
                writer.println(getIndentation(lastIndent) + "{");
                indentStack.push(indent);
            } else if (indent < lastIndent) {
                // Decrease indentation - add closing braces
                while (!indentStack.isEmpty() && indentStack.peek() > indent) {
                    indentStack.pop();
                    writer.println(getIndentation(indentStack.isEmpty() ? 0 : indentStack.peek()) + "}");
                }
            }
            
            // Write the actual line
            writer.println(line);
            lastIndent = indent;
        }
        
        // Close any remaining open braces
        while (indentStack.size() > 1) {
            indentStack.pop();
            writer.println(getIndentation(indentStack.isEmpty() ? 0 : indentStack.peek()) + "}");
        }
        
        reader.close();
        writer.close();
        
        System.out.println("Preprocessed " + inputFile + " -> " + outputFile);
    }
    
    private static String getIndentation(int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }
}

// Extension method for trimming left whitespace
extension String {
    String ltrim() {
        int i = 0;
        while (i < this.length() && Character.isWhitespace(this.charAt(i))) {
            i++;
        }
        return this.substring(i);
    }
}
