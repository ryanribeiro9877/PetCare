package com.petcare.app;

import com.petcare.dao.*;
import com.petcare.model.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;


public class PetCareApp {

    private final ProprietarioDAO proprietarioDAO = new ProprietarioDAO();
    private final VeterinarioDAO veterinarioDAO = new VeterinarioDAO();
    private final AnimalDAO animalDAO = new AnimalDAO();
    private final ConsultaDAO consultaDAO = new ConsultaDAO();

    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        PetCareApp app = new PetCareApp();
        app.run();
    }

    private void run() {
        System.out.println("=== Sistema de Gestão PetCare ===");
        int option;
        do {
            System.out.println("\nMenu Principal:");
            System.out.println("1. Gerenciar Proprietários");
            System.out.println("2. Gerenciar Veterinários");
            System.out.println("3. Gerenciar Animais");
            System.out.println("4. Gerenciar Consultas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            option = readInt();
            switch (option) {
                case 1 -> menuProprietarios();
                case 2 -> menuVeterinarios();
                case 3 -> menuAnimais();
                case 4 -> menuConsultas();
                case 0 -> System.out.println("Encerrando a aplicação.");
                default -> System.out.println("Opção inválida!");
            }
        } while (option != 0);
        scanner.close();
    }

    private void menuProprietarios() {
        int opt;
        do {
            System.out.println("\n-- Gestão de Proprietários --");
            System.out.println("1. Cadastrar proprietário");
            System.out.println("2. Listar proprietários");
            System.out.println("3. Atualizar proprietário");
            System.out.println("4. Excluir proprietário");
            System.out.println("5. Buscar por CPF");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            opt = readInt();
            try {
                switch (opt) {
                    case 1 -> cadastrarProprietario();
                    case 2 -> listarProprietarios();
                    case 3 -> atualizarProprietario();
                    case 4 -> excluirProprietario();
                    case 5 -> buscarProprietarioPorCpf();
                    case 0 -> {}
                    default -> System.out.println("Opção inválida!");
                }
            } catch (SQLException e) {
                System.err.println("Erro ao executar operação: " + e.getMessage());
            }
        } while (opt != 0);
    }

    private void cadastrarProprietario() throws SQLException {
        System.out.println("-- Cadastro de Proprietário --");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("E-mail: ");
        String email = scanner.nextLine();
        Proprietario proprietario = new Proprietario(0, nome, cpf, telefone, endereco, email);
        int id = proprietarioDAO.create(proprietario);
        System.out.println("Proprietário cadastrado com ID " + id);
    }

    private void listarProprietarios() throws SQLException {
        List<Proprietario> proprietarios = proprietarioDAO.getAll();
        System.out.println("-- Lista de Proprietários --");
        for (Proprietario p : proprietarios) {
            System.out.println(p);
        }
    }

    private void atualizarProprietario() throws SQLException {
        System.out.print("Informe o ID do proprietário para atualizar: ");
        int id = readInt();
        Proprietario p = proprietarioDAO.getById(id);
        if (p == null) {
            System.out.println("Proprietário não encontrado.");
            return;
        }
        System.out.println("Deixe em branco para manter o valor atual.");
        System.out.print("Nome (" + p.getNome() + "): ");
        String nome = scanner.nextLine();
        if (!nome.isEmpty()) p.setNome(nome);
        System.out.print("CPF (" + p.getCpf() + "): ");
        String cpf = scanner.nextLine();
        if (!cpf.isEmpty()) p.setCpf(cpf);
        System.out.print("Telefone (" + p.getTelefone() + "): ");
        String telefone = scanner.nextLine();
        if (!telefone.isEmpty()) p.setTelefone(telefone);
        System.out.print("Endereço (" + p.getEndereco() + "): ");
        String endereco = scanner.nextLine();
        if (!endereco.isEmpty()) p.setEndereco(endereco);
        System.out.print("E-mail (" + p.getEmail() + "): ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) p.setEmail(email);
        boolean ok = proprietarioDAO.update(p);
        if (ok) {
            System.out.println("Proprietário atualizado.");
        } else {
            System.out.println("Falha na atualização.");
        }
    }

    private void excluirProprietario() throws SQLException {
        System.out.print("Informe o ID do proprietário para excluir: ");
        int id = readInt();
        boolean ok = proprietarioDAO.delete(id);
        if (ok) {
            System.out.println("Proprietário excluído.");
        } else {
            System.out.println("Não foi possível excluir. Verifique se o proprietário existe e se não há animais vinculados.");
        }
    }

    private void buscarProprietarioPorCpf() throws SQLException {
        System.out.print("CPF do proprietário: ");
        String cpf = scanner.nextLine();
        Proprietario p = proprietarioDAO.getByCpf(cpf);
        if (p != null) {
            System.out.println(p);
        } else {
            System.out.println("Proprietário não encontrado.");
        }
    }

    private void menuVeterinarios() {
        int opt;
        do {
            System.out.println("\n-- Gestão de Veterinários --");
            System.out.println("1. Cadastrar veterinário");
            System.out.println("2. Listar veterinários");
            System.out.println("3. Atualizar veterinário");
            System.out.println("4. Excluir veterinário");
            System.out.println("5. Buscar por CRMV");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            opt = readInt();
            try {
                switch (opt) {
                    case 1 -> cadastrarVeterinario();
                    case 2 -> listarVeterinarios();
                    case 3 -> atualizarVeterinario();
                    case 4 -> excluirVeterinario();
                    case 5 -> buscarVeterinarioPorCrmv();
                    case 0 -> {}
                    default -> System.out.println("Opção inválida!");
                }
            } catch (SQLException e) {
                System.err.println("Erro ao executar operação: " + e.getMessage());
            }
        } while (opt != 0);
    }

    private void cadastrarVeterinario() throws SQLException {
        System.out.println("-- Cadastro de Veterinário --");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CRMV: ");
        String crmv = scanner.nextLine();
        System.out.print("Especialidade: ");
        String especialidade = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        Veterinario v = new Veterinario(0, nome, crmv, especialidade, telefone);
        int id = veterinarioDAO.create(v);
        System.out.println("Veterinário cadastrado com ID " + id);
    }

    private void listarVeterinarios() throws SQLException {
        List<Veterinario> vets = veterinarioDAO.getAll();
        System.out.println("-- Lista de Veterinários --");
        for (Veterinario v : vets) {
            System.out.println(v);
        }
    }

    private void atualizarVeterinario() throws SQLException {
        System.out.print("Informe o ID do veterinário para atualizar: ");
        int id = readInt();
        Veterinario v = veterinarioDAO.getById(id);
        if (v == null) {
            System.out.println("Veterinário não encontrado.");
            return;
        }
        System.out.println("Deixe em branco para manter o valor atual.");
        System.out.print("Nome (" + v.getNome() + "): ");
        String nome = scanner.nextLine();
        if (!nome.isEmpty()) v.setNome(nome);
        System.out.print("CRMV (" + v.getCrmv() + "): ");
        String crmv = scanner.nextLine();
        if (!crmv.isEmpty()) v.setCrmv(crmv);
        System.out.print("Especialidade (" + v.getEspecialidade() + "): ");
        String esp = scanner.nextLine();
        if (!esp.isEmpty()) v.setEspecialidade(esp);
        System.out.print("Telefone (" + v.getTelefone() + "): ");
        String tel = scanner.nextLine();
        if (!tel.isEmpty()) v.setTelefone(tel);
        boolean ok = veterinarioDAO.update(v);
        if (ok) {
            System.out.println("Veterinário atualizado.");
        } else {
            System.out.println("Falha na atualização.");
        }
    }

    private void excluirVeterinario() throws SQLException {
        System.out.print("Informe o ID do veterinário para excluir: ");
        int id = readInt();
        boolean ok = veterinarioDAO.delete(id);
        if (ok) {
            System.out.println("Veterinário excluído.");
        } else {
            System.out.println("Não foi possível excluir. Verifique se o veterinário existe e se não há consultas vinculadas.");
        }
    }

    private void buscarVeterinarioPorCrmv() throws SQLException {
        System.out.print("CRMV do veterinário: ");
        String crmv = scanner.nextLine();
        Veterinario v = veterinarioDAO.getByCrmv(crmv);
        if (v != null) {
            System.out.println(v);
        } else {
            System.out.println("Veterinário não encontrado.");
        }
    }

    private void menuAnimais() {
        int opt;
        do {
            System.out.println("\n-- Gestão de Animais --");
            System.out.println("1. Cadastrar animal");
            System.out.println("2. Listar animais");
            System.out.println("3. Atualizar animal");
            System.out.println("4. Excluir animal");
            System.out.println("5. Listar animais por proprietário");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            opt = readInt();
            try {
                switch (opt) {
                    case 1 -> cadastrarAnimal();
                    case 2 -> listarAnimais();
                    case 3 -> atualizarAnimal();
                    case 4 -> excluirAnimal();
                    case 5 -> listarAnimaisPorProprietario();
                    case 0 -> {}
                    default -> System.out.println("Opção inválida!");
                }
            } catch (SQLException e) {
                System.err.println("Erro ao executar operação: " + e.getMessage());
            }
        } while (opt != 0);
    }

    private void cadastrarAnimal() {
        System.out.println("\n-- Cadastro de Animal --");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Espécie: ");
        String especie = lerEspecie();


        System.out.print("Raça: ");
        String raca = scanner.nextLine();

        LocalDate dataNascimento = null;
        while (dataNascimento == null) {
            System.out.print("Data de Nascimento (dd/MM/yyyy): ");
            String dataStr = scanner.nextLine().trim();

            if (dataStr.isEmpty()) {
                System.out.println("⚠ A data de nascimento não pode ficar em branco. Tente novamente.");
                continue;
            }

            try {
                DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                dataNascimento = LocalDate.parse(dataStr, df);
            } catch (Exception e) {
                System.out.println("❌ Data inválida! Use o formato dd/MM/yyyy (ex: 09/08/2021).");
            }
        }

        System.out.print("Peso (kg): ");
        double peso = Double.parseDouble(scanner.nextLine());

        System.out.print("ID do proprietário responsável: ");
        int proprietarioId = Integer.parseInt(scanner.nextLine());

        Animal animal = new Animal(0, nome, especie, raca, dataNascimento, peso, proprietarioId);

        try {
            animalDAO.create(animal);
            System.out.println("✅ Animal cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("❌ Erro ao cadastrar animal: " + e.getMessage());
        }
    }

    private void listarAnimais() throws SQLException {
        List<Animal> animals = animalDAO.getAll();
        System.out.println("-- Lista de Animais --");
        for (Animal a : animals) {
            System.out.println(a);
        }
    }

    private void atualizarAnimal() throws SQLException {
        System.out.print("Informe o ID do animal para atualizar: ");
        int id = readInt();
        Animal a = animalDAO.getById(id);
        if (a == null) {
            System.out.println("Animal não encontrado.");
            return;
        }
        System.out.println("Deixe em branco para manter o valor atual.");
        System.out.print("Nome (" + a.getNome() + "): ");
        String nome = scanner.nextLine();
        if (!nome.isEmpty()) a.setNome(nome);
        System.out.print("Espécie (" + a.getEspecie() + "): ");
        String especie = scanner.nextLine();
        if (!especie.isEmpty()) a.setEspecie(especie);
        System.out.print("Raça (" + a.getRaca() + "): ");
        String raca = scanner.nextLine();
        if (!raca.isEmpty()) a.setRaca(raca);
        System.out.print("Data de Nascimento (" + a.getDataNascimento() + "): ");
        String dataStr = scanner.nextLine();
        if (!dataStr.isEmpty()) a.setDataNascimento(LocalDate.parse(dataStr));
        System.out.print("Peso (" + a.getPeso() + "): ");
        String pesoStr = scanner.nextLine();
        if (!pesoStr.isEmpty()) a.setPeso(Double.parseDouble(pesoStr));
        System.out.print("ID do Proprietário (" + a.getProprietarioId() + "): ");
        String propStr = scanner.nextLine();
        if (!propStr.isEmpty()) a.setProprietarioId(Integer.parseInt(propStr));
        boolean ok = animalDAO.update(a);
        if (ok) {
            System.out.println("Animal atualizado.");
        } else {
            System.out.println("Falha na atualização.");
        }
    }

    private void excluirAnimal() throws SQLException {
        System.out.print("Informe o ID do animal para excluir: ");
        int id = readInt();
        boolean ok = animalDAO.delete(id);
        if (ok) {
            System.out.println("Animal excluído.");
        } else {
            System.out.println("Não foi possível excluir. Verifique se o animal existe e se não há consultas vinculadas.");
        }
    }

    private void listarAnimaisPorProprietario() throws SQLException {
        System.out.print("Informe o ID do proprietário: ");
        int propId = readInt();
        List<Animal> animals = animalDAO.getByProprietarioId(propId);
        System.out.println("-- Animais do Proprietário " + propId + " --");
        for (Animal a : animals) {
            System.out.println(a);
        }
    }

    private void menuConsultas() {
        int opt;
        do {
            System.out.println("\n-- Gestão de Consultas --");
            System.out.println("1. Cadastrar consulta");
            System.out.println("2. Listar consultas");
            System.out.println("3. Atualizar consulta");
            System.out.println("4. Excluir consulta");
            System.out.println("5. Listar consultas por CPF do proprietário");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            opt = readInt();
            try {
                switch (opt) {
                    case 1 -> cadastrarConsulta();
                    case 2 -> listarConsultas();
                    case 3 -> atualizarConsulta();
                    case 4 -> excluirConsulta();
                    case 5 -> listarConsultasPorCpf();
                    case 0 -> {}
                    default -> System.out.println("Opção inválida!");
                }
            } catch (SQLException e) {
                System.err.println("Erro ao executar operação: " + e.getMessage());
            }
        } while (opt != 0);
    }
    
    

    private String lerEspecie() {
        while (true) {
            System.out.println("Escolha a espécie:");
            System.out.println("1 - Cachorro");
            System.out.println("2 - Gato");
            System.out.println("3 - Ave");
            System.out.println("4 - Peixe");
            System.out.println("5 - Hamster");
            System.out.println("6 - Outros");

            System.out.print("Opção (1-6): ");
            String opcaoStr = scanner.nextLine().trim();

            if (opcaoStr.isEmpty()) {
                System.out.println("⚠ A opção não pode ficar em branco. Tente novamente.");
                continue;
            }

            int opcao;
            try {
                opcao = Integer.parseInt(opcaoStr);
            } catch (NumberFormatException e) {
                System.out.println("❌ Opção inválida! Digite um número de 1 a 6.");
                continue;
            }

            switch (opcao) {
                case 1:
                    return "Cachorro";
                case 2:
                    return "Gato";
                case 3:
                    return "Ave";
                case 4:
                    return "Peixe";
                case 5:
                    return "Hamster";
                case 6:
                    System.out.print("Digite a espécie: ");
                    String outra = scanner.nextLine().trim();
                    if (outra.isEmpty()) {
                        System.out.println("⚠ A espécie não pode ficar em branco.");
                        break; // volta pro while
                    }
                    return outra;
                default:
                    System.out.println("❌ Opção inválida! Digite um número de 1 a 6.");
            }
        }
    }


    private void cadastrarConsulta() {
        System.out.println("\n-- Cadastro de Consulta --");

       
        System.out.print("ID do animal: ");
        int animalId = Integer.parseInt(scanner.nextLine());


        System.out.print("ID do veterinário: ");
        int veterinarioId = Integer.parseInt(scanner.nextLine());

        LocalDate data = null;
        while (data == null) {
            System.out.print("Digite a data da consulta (dd/MM/yyyy): ");
            String dataStr = scanner.nextLine().trim();

            if (dataStr.isEmpty()) {
                System.out.println("⚠ A data não pode ficar em branco. Tente novamente.");
                continue;
            }

            try {
                DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                data = LocalDate.parse(dataStr, df);
            } catch (Exception e) {
                System.out.println("❌ Data inválida! Use o formato dd/MM/yyyy");
            }
        }

        LocalTime hora = null;
        while (hora == null) {
            System.out.print("Digite o horário (HH:mm): ");
            String horaStr = scanner.nextLine().trim();

            if (horaStr.isEmpty()) {
                System.out.println("⚠ O horário não pode ficar em branco. Tente novamente.");
                continue;
            }

            try {
                DateTimeFormatter hf = DateTimeFormatter.ofPattern("HH:mm");
                hora = LocalTime.parse(horaStr, hf);
            } catch (Exception e) {
                System.out.println("❌ Horário inválido! Use o formato HH:mm");
            }
        }

        LocalDateTime dataHora = LocalDateTime.of(data, hora);

        if (consultaDAO.existeConsultaNoHorario(dataHora)) {
            System.out.println("❌ Horário indisponível! Já existe uma consulta nesse horário.");
            System.out.println("Horários disponíveis para esse dia:");

            List<LocalTime> disponiveis = consultaDAO.horariosDisponiveis(data);

            for (LocalTime h : disponiveis) {
                System.out.println("  → " + h.toString());
            }

            return;
        }

        System.out.print("Diagnóstico: ");
        String diagnostico = scanner.nextLine();

        System.out.print("Valor: ");
        double valor = Double.parseDouble(scanner.nextLine());

        Consulta consulta = new Consulta(0, dataHora, animalId, veterinarioId, diagnostico, valor);

        try {
            consultaDAO.create(consulta);
            System.out.println("✅ Consulta cadastrada com sucesso!");
        } catch (Exception e) {
            System.out.println("❌ Erro ao cadastrar consulta: " + e.getMessage());
        }
    }


    private void listarConsultas() {
    	
        System.out.println("\n-- Lista de Consultas --");

        try {
            List<Consulta> consultas = consultaDAO.getAll();

            if (consultas.isEmpty()) {
                System.out.println("Nenhuma consulta cadastrada.");
                return;
            }

            for (Consulta c : consultas) {
                Animal animal = animalDAO.getById(c.getAnimalId());
                Veterinario vet = veterinarioDAO.getById(c.getVeterinarioId());

                String nomeAnimal = (animal != null) ? animal.getNome() : "(animal não encontrado)";
                String nomeVet    = (vet != null)    ? vet.getNome()    : "(veterinário não encontrado)";

                String dataHoraFmt = c.getDataHora()
                        .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

                System.out.printf(
                    "ID: %d | Data/Hora: %s | Animal: %s | Veterinário: %s | Diagnóstico: %s | Valor: R$ %.2f%n",
                    c.getId(),
                    dataHoraFmt,
                    nomeAnimal,
                    nomeVet,
                    c.getDiagnostico(),
                    c.getValor()
                    
                );
                
            }

        } catch (Exception e) {
            System.out.println("❌ Erro ao listar consultas: " + e.getMessage());
        }
    }


    private void atualizarConsulta() throws SQLException {
        System.out.print("Informe o ID da consulta para atualizar: ");
        int id = readInt();
        Consulta c = consultaDAO.getById(id);
        if (c == null) {
            System.out.println("Consulta não encontrada.");
            return;
        }
        System.out.println("Deixe em branco para manter o valor atual.");
        System.out.print("Data e hora (" + c.getDataHora().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "): ");
        String dataHoraStr = scanner.nextLine();
        if (!dataHoraStr.isEmpty()) {
            c.setDataHora(LocalDateTime.parse(dataHoraStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }
        System.out.print("ID do Animal (" + c.getAnimalId() + "): ");
        String animalIdStr = scanner.nextLine();
        if (!animalIdStr.isEmpty()) {
            c.setAnimalId(Integer.parseInt(animalIdStr));
        }
        System.out.print("ID do Veterinário (" + c.getVeterinarioId() + "): ");
        String vetIdStr = scanner.nextLine();
        if (!vetIdStr.isEmpty()) {
            c.setVeterinarioId(Integer.parseInt(vetIdStr));
        }
        System.out.print("Diagnóstico (" + c.getDiagnostico() + "): ");
        String diag = scanner.nextLine();
        if (!diag.isEmpty()) {
            c.setDiagnostico(diag);
        }
        System.out.print("Valor (" + c.getValor() + "): ");
        String valorStr = scanner.nextLine();
        if (!valorStr.isEmpty()) {
            c.setValor(Double.parseDouble(valorStr));
        }
        boolean ok = consultaDAO.update(c);
        if (ok) {
            System.out.println("Consulta atualizada.");
        } else {
            System.out.println("Falha na atualização.");
        }
    }

    private void excluirConsulta() throws SQLException {
        System.out.print("Informe o ID da consulta para excluir: ");
        int id = readInt();
        boolean ok = consultaDAO.delete(id);
        if (ok) {
            System.out.println("Consulta excluída.");
        } else {
            System.out.println("Não foi possível excluir. Verifique se a consulta existe.");
        }
    }

    private void listarConsultasPorCpf() throws SQLException {
        System.out.print("Informe o CPF do proprietário: ");
        String cpf = scanner.nextLine();
        List<Consulta> consultas = consultaDAO.getByOwnerCpf(cpf);
        System.out.println("-- Consultas do proprietário com CPF " + cpf + " --");
        for (Consulta c : consultas) {
            System.out.println(c);
        }
    }

    private int readInt() {
        String line;
        do {
            line = scanner.nextLine();
            if (line.isBlank()) {
                System.out.print("Digite um número válido: ");
                continue;
            }
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.print("Valor inválido, tente novamente: ");
            }
        } while (true);
    }
}
