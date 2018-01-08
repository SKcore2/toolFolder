package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;

public class Controller implements Initializable {

	@FXML
	private TextArea textArea;

	@FXML
	private ListView<String> fileList;

	@FXML
	private ListView<String> addList;

	@FXML
	private ComboBox<String> gitActionBox;

	@FXML
	private Button actionButton;

	@FXML
	private Button moveToRight;

	@FXML
	private Button moveToLeft;

	@FXML
	private Button referButton;

	@FXML
	private TextField commitMessage;

	@FXML
	private TextField pathTextField;

	@FXML
	private Pane parentsDialog;

	@FXML
	private Pane addPane;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<String> listValue = new ArrayList<>();
		for (Command command : Command.values()) {
			listValue.add(command.name());
		}
		listValue.stream().forEach(value -> gitActionBox.getItems().add(value));
		String initialText = getTxtFile(System.getProperty("user.dir") + "Path.txt");
		System.out.println(initialText);
		pathTextField.setText(initialText);
	}

	@FXML
	public void onClickActionButton(ActionEvent event) throws IOException {
		Command gitCommand = Command.valueOf(gitActionBox.getValue());

		switch (gitCommand) {
		case Status:
			setResultText(ExecuteBatch.executeBatch(gitCommand, pathTextField.getText()));
			break;
		case Add:
			setResultText(ExecuteBatch.executeBatchAdd(gitCommand, pathTextField.getText(), addList.getItems()));
			break;
		case Commit:
			if (commitMessage.getText() != null) {
				setResultText(ExecuteBatch.executeBatchTwoArguments(gitCommand, pathTextField.getText(),
						commitMessage.getText()));
				commitMessage.setVisible(false);
				commitMessage.clear();
			} else {

			}
			break;
		case Push:
			if (commitMessage.getText() != null) {
				setResultText(ExecuteBatch.executeBatchTwoArguments(gitCommand, pathTextField.getText(),
						commitMessage.getText()));
			} else {
				setResultText(ExecuteBatch.executeBatchTwoArguments(gitCommand, pathTextField.getText(), "master"));
			}
			break;
		case Pull:
			if (commitMessage.getText() != null) {
				setResultText(ExecuteBatch.executeBatchTwoArguments(gitCommand, pathTextField.getText(),
						commitMessage.getText()));
				commitMessage.setVisible(false);
				commitMessage.clear();
			} else {
				setResultText(ExecuteBatch.executeBatchTwoArguments(gitCommand, pathTextField.getText(), "master"));
			}
			break;
		default:
			break;
		}
	}

	@FXML
	public void changeComboBox(ActionEvent event) throws IOException {
		Command gitCommand = Command.valueOf(gitActionBox.getValue());
		fileList.getItems().clear();
		addList.getItems().clear();
		switch (gitCommand) {
		case Status:
			System.out.println("Status。");
			break;
		case Add:
			System.out.println("Addです。");
			addPane.setVisible(true);
			setResultText(ExecuteBatch.executeBatch(Command.Status, pathTextField.getText()));
			setFileName(getFileList(pathTextField.getText()));
			break;
		case Commit:
			setResultText(ExecuteBatch.executeBatch(Command.Status, pathTextField.getText()));
			setFileName(getFileList(pathTextField.getText()));
			commitMessage.setVisible(true);
			System.out.println("Commitです。");
			break;
		case Push:
			commitMessage.setVisible(true);
			commitMessage.setPromptText("push先を入力してください");
			break;
		default:
			System.out.println("Other!!");
			break;
		}
	}

	@FXML
	public void onMoveToRight() {
		if (!fileList.getSelectionModel().getSelectedItem().isEmpty()) {
			addList.getItems().add(fileList.getSelectionModel().getSelectedItem());
			fileList.getItems().remove(fileList.getSelectionModel().getSelectedItem());
		}
	}

	public void onMoveToLeft() {
		if (!addList.getSelectionModel().getSelectedItem().isEmpty()) {
			fileList.getItems().add(addList.getSelectionModel().getSelectedItem());
			addList.getItems().remove(addList.getSelectionModel().getSelectedItem());
		}
	}

	@FXML
	public void onClickOpenDialog() {
		//単一ファイルを選択
		final DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		File selectDirectory = directoryChooser.showDialog(null);
		if (selectDirectory != null) {
			pathTextField.setText(selectDirectory.toString());

			try {
				String abusolutePath = System.getProperty("user.dir");
				File file = new File(abusolutePath + "Path.txt");
				FileWriter filewriter = new FileWriter(file);

				filewriter.write(selectDirectory.toString());

				filewriter.close();
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}

	private void setResultText(String resultTxt) {
		textArea.setText(resultTxt);
	}

	private void setFileName(List<String> fileName) {
		fileName.stream().forEach(file -> {
			fileList.getItems().add(file);
		});
	}

	private void onRepositoryField(ActionEvent event) {
	}


	private List<String> getFileList(String path) {
		File dir = new File(path);
		File[] files = dir.listFiles();
		List<String> fileList = new ArrayList<>();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			fileList.add(file.toString());
			System.out.println((i + 1) + ":    " + file);
		}
		return fileList;
	}

	private String getTxtFile(String path) {
		  String str = null;
		try{
			  File file = new File(path);
			  BufferedReader br = new BufferedReader(new FileReader(file));
			  str = br.readLine();
			  br.close();
			}catch(FileNotFoundException e){
			  System.out.println(e);
			}catch(IOException e){
			  System.out.println(e);
			}
		  return str;
	}
}