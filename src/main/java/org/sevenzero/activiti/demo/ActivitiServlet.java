package org.sevenzero.activiti.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.repository.ProcessDefinition;

/**
 * Servlet implementation class ActivitiServlet
 */
@WebServlet("/activitiServlet")
public class ActivitiServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	static final Logger log = Logger.getLogger(ActivitiServlet.class.getSimpleName());
	
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ActivitiServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("image/png");
		
		log.info("##");
		
		String key = "example";
		key = "myProcess";
		RepositoryService rs = processEngine.getRepositoryService();
		HistoryService hs = processEngine.getHistoryService();
		RuntimeService runtime = processEngine.getRuntimeService();
		
		ProcessDefinition pd = rs.createProcessDefinitionQuery().processDefinitionKey(key).latestVersion().singleResult();
		BpmnModel bm = rs.getBpmnModel(pd.getId());
		
		int id = 10001;
		id = 2501;
		List<HistoricActivityInstance> hai = hs.createHistoricActivityInstanceQuery().processInstanceId("" + id).orderByHistoricActivityInstanceId().asc().list();
		
		// 已执行的节点ID集合
        List<String> executedActivityIdList = new ArrayList<>();
        for (HistoricActivityInstance activityInstance : hai) {
            executedActivityIdList.add(activityInstance.getActivityId());
        }
        
        ProcessEngineConfiguration pec = processEngine.getProcessEngineConfiguration();
        log.info(pec.getActivityFontName() + ", " + pec.getAnnotationFontName() + ", " + pec.getLabelFontName());
		
		try (
//				InputStream is = processEngine.getProcessEngineConfiguration()
//								.getProcessDiagramGenerator().generateDiagram(bm, "png", executedActivityIdList);
				
				InputStream is = processEngine.getProcessEngineConfiguration()
								.getProcessDiagramGenerator().generateDiagram(bm, "png", Collections.<String>emptyList(), Collections.<String>emptyList(), "WenQuanYi Micro Hei", "WenQuanYi Micro Hei", "WenQuanYi Micro Hei", null, 1.0);
				
//				InputStream is = processEngine.getProcessEngineConfiguration()
//								.getProcessDiagramGenerator().generatePngDiagram(bm);
//				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("/home/lb/temp/image/a.png"));
				OutputStream bos = response.getOutputStream();
		) {
			int size = 2048;
			byte[] buffer = new byte[size];
			int len = 0;
			while ( (len = is.read(buffer, 0, size)) > -1 ) {
				bos.write(buffer, 0, len);
			}
			bos.flush();
		} 
		catch (IOException e) {
			log.info("输出流出现异常");
			e.printStackTrace();
		}
		
	}

}
