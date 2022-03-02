package model.util;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import application.LoadData;
import model.entities.Change;
import model.entities.User;
import model.services.change.ChangeService;

public class DefaultChanges {

	private final ChangeService service = new ChangeService();

	private List<Change> listOriginal;
	private List<Change> listDefault = new ArrayList<Change>();
	private List<Change> listNew = new ArrayList<Change>();

	public DefaultChanges() {
		listOriginal = LoadData.getChangesList();
		sort();
	}

	private void sort() {
		
		for (Change change : listOriginal) {
			
			String type = change.getType();
			String changes = change.getChanges();
			
			//ToCheck Equipment
			if (type.equals("Entrada de Equipamento") && changes.equals("Novo Equipamento Adicionado")) {
				change.setType("Equipment Input");
				change.setChanges("New Equipment Added");
				listDefault.add(change);
			}
			
			//ToCheck Monitor
			if (type.equals("Entrada de Monitor") && changes.equals("Novo Monitor Adicionado")) {
				change.setType("Monitor Input");
				change.setChanges("New Monitor Added");
				listDefault.add(change);
			}
			
			//ToCheck User
			if (type.equals("Entrada de Usuário") && changes.equals("Novo Usuário Adicionado")) {
				change.setType("User Input");
				change.setChanges("New User Added");
				listDefault.add(change);
			}
			
			//ToCheck Project
			if (type.equals("Entrada de Projeto") && changes.equals("Novo Projeto Adicionado")) {
				change.setType("Project Input");
				change.setChanges("New Project Added");
				listDefault.add(change);
			}
			
			//ToCheck Work Position
			if (type.equals("Entrada de Posição de Trabalho") && changes.equals("Nova Posição de Trabalho Adicionada")) {
				change.setType("Work Position Input");
				change.setChanges("New Work Position Added");
				listDefault.add(change);
			}
			
			//ToCheck Equipment Exit
			if (type.equals("Saída de Inventário")) {
				
				if (changes.contains("Monitor entregue ao usuário:")) {
					change.setType("Monitor Exit");
					
					String userOld = changes.substring(29);
					change.setChanges("Monitor delivered to the user: " + userOld);
					listDefault.add(change);
					
					final User user = LoadData.getUserByName(userOld);
					if (user != null && change.getObject() != null || !change.getObject().equals("")) {
						final Change changeUser = new Change();
						changeUser.setObject(user.getRegistration());
						changeUser.setType("Monitor Exit");
						changeUser.setChanges("Monitor "+ change.getObject() + " delivered to the user");
						changeUser.setDate(change.getDate());
						changeUser.setAuthor(change.getAuthor());
						listNew.add(changeUser);
					}
				} 
				else if (changes.contains("Equipamento entregue ao usuário:")) {
					change.setType("Equipment Exit");
					
					String userOld = changes.substring(33);
					change.setChanges("Equipment delivered to the user: " + userOld);
					listDefault.add(change);
					
					final User user = LoadData.getUserByName(userOld);
					if (user != null && change.getObject() != null || !change.getObject().equals("")) {
						final Change changeUser = new Change();
						changeUser.setObject(user.getRegistration());
						changeUser.setType("Equipment Exit");
						changeUser.setChanges("Equipment "+ change.getObject() + " delivered to the user");
						changeUser.setDate(change.getDate());
						changeUser.setAuthor(change.getAuthor());
						listNew.add(changeUser);
					}
				}
			}
			
			//ToCheck Equipment Return
			if (type.equals("Entrada de Inventário")) {
				
				if (changes.contains("Monitor devolvido pelo usuário:")) {
					change.setType("Monitor Return");
					
					String userOld = changes.substring(32);
					change.setChanges("Monitor returned by the user: " + userOld);
					listDefault.add(change);
					
					final User user = LoadData.getUserByName(userOld);
					if (user != null && change.getObject() != null || !change.getObject().equals("")) {
						final Change changeUser = new Change();
						changeUser.setObject(user.getRegistration());
						changeUser.setType("Monitor Return");
						changeUser.setChanges("Monitor "+ change.getObject() + " returned by the user");
						changeUser.setDate(change.getDate());
						changeUser.setAuthor(change.getAuthor());
						listNew.add(changeUser);
					}
				} 
				else if (changes.contains("Equipamento devolvido pelo usuário:")) {
					change.setType("Equipment Return");
					
					String userOld = changes.substring(36);
					change.setChanges("Equipment returned by the user: " + userOld);
					listDefault.add(change);
					
					final User user = LoadData.getUserByName(userOld);
					if (user != null && change.getObject() != null || !change.getObject().equals("")) {
						final Change changeUser = new Change();
						changeUser.setObject(user.getRegistration());
						changeUser.setType("Equipment Return");
						changeUser.setChanges("Equipment "+ change.getObject() + " returned by the user");
						changeUser.setDate(change.getDate());
						changeUser.setAuthor(change.getAuthor());
						listNew.add(changeUser);
					}
				}
			}
			
			if (type.equals("Alteração de Inventário")) {
				
				if (changes.contains("Monitor devolvido pelo usuário:")) {
					change.setType("Monitor Exchange");
					
					String userOld = changes.substring(32);
					change.setChanges("Monitor returned by the user: " + userOld);
					listDefault.add(change);
					
					final User user = LoadData.getUserByName(userOld);
					if (user != null && change.getObject() != null || !change.getObject().equals("")) {
						final Change changeUser = new Change();
						changeUser.setObject(user.getRegistration());
						changeUser.setType("Monitor Exchange");
						changeUser.setChanges("Monitor "+ change.getObject() + " returned by the user");
						changeUser.setDate(change.getDate());
						changeUser.setAuthor(change.getAuthor());
						listNew.add(changeUser);
					}
				} 
				else if (changes.contains("Equipamento devolvido pelo usuário:")) {
					change.setType("Equipment Exchange");
					
					String userOld = changes.substring(36);
					change.setChanges("Equipment returned by the user: " + userOld);
					listDefault.add(change);
					
					final User user = LoadData.getUserByName(userOld);
					if (user != null && change.getObject() != null || !change.getObject().equals("")) {
						final Change changeUser = new Change();
						changeUser.setObject(user.getRegistration());
						changeUser.setType("Equipment Exchange");
						changeUser.setChanges("Equipment "+ change.getObject() + " returned by the user");
						changeUser.setDate(change.getDate());
						changeUser.setAuthor(change.getAuthor());
						listNew.add(changeUser);
					}
				}
				
				if (changes.contains("Monitor entregue ao usuário:")) {
					change.setType("Monitor Exchange");
					
					String userOld = changes.substring(29);
					change.setChanges("Monitor delivered to the user: " + userOld);
					listDefault.add(change);
					
					final User user = LoadData.getUserByName(userOld);
					if (user != null && change.getObject() != null || !change.getObject().equals("")) {
						final Change changeUser = new Change();
						changeUser.setObject(user.getRegistration());
						changeUser.setType("Monitor Exit");
						changeUser.setChanges("Monitor "+ change.getObject() + " delivered to the user");
						changeUser.setDate(change.getDate());
						changeUser.setAuthor(change.getAuthor());
						listNew.add(changeUser);
					}
				} 
				else if (changes.contains("Equipamento entregue ao usuário:")) {
					change.setType("Equipment Exchange");
					
					String userOld = changes.substring(33);
					change.setChanges("Equipment delivered to the user: " + userOld);
					listDefault.add(change);
					
					final User user = LoadData.getUserByName(userOld);
					if (user != null && change.getObject() != null || !change.getObject().equals("")) {
						final Change changeUser = new Change();
						changeUser.setObject(user.getRegistration());
						changeUser.setType("Equipment Exit");
						changeUser.setChanges("Equipment "+ change.getObject() + " delivered to the user");
						changeUser.setDate(change.getDate());
						changeUser.setAuthor(change.getAuthor());
						listNew.add(changeUser);
					}
				}
				
			}
			
		}

		if (listDefault.size() > 0 || listNew.size() > 0) {
			
			for (Change change : listDefault) {
				System.out.println(change);
				//service.updateDefault(change);
			}
			
			for (Change change : listNew) {
				System.out.println(change);
				//service.insert(change);
			}
			
			JOptionPane.showMessageDialog(null, "Defatul list", null, JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "List is already dafault", null, JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
