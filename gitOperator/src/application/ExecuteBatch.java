package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class ExecuteBatch {
	public static String executeBatch(Command gitCommand, String path) {
		  String abusolutePath = System.getProperty("user.dir");
		String cmds[] = { "cmd.exe", "/c", "start",
				abusolutePath + "\\gitOperator.bat", gitCommand.name(),path };
		String resultText = null;
		try {
			Process executeGitCommand = Runtime.getRuntime().exec(cmds);

			int returnValue = executeGitCommand.waitFor();

			if (returnValue == 0) {
				resultText = getResultText(
						(abusolutePath + "\\result.txt"));
			} else {
				resultText = "error";
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return resultText;
	}

	public static String executeBatchAdd(Command gitCommand,String path, List<String> addFileList) throws IOException {
		  String abusolutePath = System.getProperty("user.dir");

		addFileList.stream().forEach(addFile -> {
			String cmds[] = { "cmd.exe", "/c", "start",
					abusolutePath + "\\gitOperator.bat",
					gitCommand.name(), path, addFile };

			try {
				Process executeGitCommand = Runtime.getRuntime().exec(cmds);

				int returnValue = executeGitCommand.waitFor();
				System.out.println(returnValue);
				if (returnValue == 0) {
				} else {
				}
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		});
		String resultText = getResultText(
				(abusolutePath + "\\result.txt"));
		return resultText;

	}

	public static String executeBatchTwoArguments(Command gitCommand,String path, String commitMessage) {
		  String abusolutePath = System.getProperty("user.dir");

		String cmds[] = { "cmd.exe", "/c", "start",
				abusolutePath + "\\gitOperator.bat", gitCommand.name(),path,
				commitMessage };
		String resultText = null;
		try {
			Process executeGitCommand = Runtime.getRuntime().exec(cmds);

			int returnValue = executeGitCommand.waitFor();
			if (returnValue == 0) {
				resultText = getResultText(
						(abusolutePath + "\\result.txt"));
			} else {
				resultText = "error";
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return resultText;
	}

	public static String executeBatchTest(Command gitCommand, String path) throws IOException, InterruptedException {
		  String abusolutePath = System.getProperty("user.dir");
		String cmds[] = { "cmd.exe", "/c", "start",
				abusolutePath + "\\gitOperator.bat", gitCommand.name(),path };
		String resultText = null;
			Process executeGitCommand = Runtime.getRuntime().exec(cmds);

			InputStream is = executeGitCommand.getErrorStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			try {
			for (;;) {
			resultText = br.readLine();
			if (resultText == null)
			break;
			System.out.println(resultText);
			}
			} finally {
			br.close();
			};
			return resultText;
	}


	public static String getResultText(final String path) throws IOException {
		return Files.lines(Paths.get(path), Charset.forName("UTF-8"))
				.collect(Collectors.joining(System.getProperty("line.separator")));
	}

}