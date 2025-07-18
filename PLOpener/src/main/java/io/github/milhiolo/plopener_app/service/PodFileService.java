package io.github.milhiolo.plopener_app.service; // Certifique-se do pacote correto

import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.ProjectReader;
import net.sf.mpxj.UniversalProjectReader;
import net.sf.mpxj.Task; // <-- ESTA AGORA SERÁ USADA
import net.sf.mpxj.Resource; // <-- ESTA AGORA SERÁ USADA
import net.sf.mpxj.Assignment; // <-- PROVAVELMENTE TAMBÉM SERÁ USADA
// ... outras importações ...

import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PodFileService {

    public Map<String, Object> processFile(String filePath) throws IOException {
        ProjectReader reader = new UniversalProjectReader();
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new IOException("Arquivo não encontrado no caminho: " + filePath);
            }
            ProjectFile project = reader.read(file);

            // Objeto para armazenar todos os dados que você quer retornar
            Map<String, Object> projectData = new HashMap<>();

            // 1. Informações do Projeto
            projectData.put("projectName", project.getProjectProperties().getName());
            projectData.put("startDate", project.getProjectProperties().getStartDate());
            projectData.put("finishDate", project.getProjectProperties().getFinishDate());

            // 2. Extrair Tarefas
            List<Map<String, Object>> tasksList = new ArrayList<>();
            for (Task task : project.getTasks()) { // <--- AQUI VOCÊ USA A CLASSE TASK
                Map<String, Object> taskInfo = new HashMap<>();
                taskInfo.put("id", task.getID());
                taskInfo.put("name", task.getName());
                taskInfo.put("start", task.getStart());
                taskInfo.put("finish", task.getFinish());
                taskInfo.put("duration", task.getDuration());
                taskInfo.put("percentComplete", task.getPercentageComplete());
                // Adicione mais propriedades da tarefa conforme necessário
                tasksList.add(taskInfo);
            }
            projectData.put("tasks", tasksList);

            // 3. Extrair Recursos
            List<Map<String, Object>> resourcesList = new ArrayList<>();
            for (Resource resource : project.getResources()) { // <--- AQUI VOCÊ USA A CLASSE RESOURCE
                Map<String, Object> resourceInfo = new HashMap<>();
                resourceInfo.put("id", resource.getID());
                resourceInfo.put("name", resource.getName());
                resourceInfo.put("type", resource.getType()); // Work, Material, Cost
                // Adicione mais propriedades do recurso conforme necessário
                resourcesList.add(resourceInfo);
            }
            projectData.put("resources", resourcesList);

            // 4. Extrair Atribuições (se necessário)
            // for (Assignment assignment : project.getAssignments()) { ... }

            return projectData; // Retorna um mapa contendo todas as informações

            // Se você fosse gerar um Excel ou PDF:
            // byte[] generatedFileBytes = YourExcelOrPdfGenerator.generate(projectData);
            // return generatedFileBytes;

        } catch (Exception e) {
            // Logar o erro e lançar uma exceção de negócio ou IOException
            throw new IOException("Erro ao ler ou processar o arquivo .pod: " + e.getMessage(), e);
        }
    }
}