package myapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Scheduler {

    static EDFScheduledTasks schedule(final List<Task> taskList, int lcm) {

        ArrayList<Integer> dead_lines = new ArrayList<>();
        HashMap<String, Integer> scheduleMap = new HashMap<>();
        ScheduledTasks out = new ScheduledTasks();
        Map<Integer, List<Task>> waitingMap = new HashMap<>();

        for (int timeUnit = 0; timeUnit < lcm; timeUnit++) {
            out.getDeadlinesList().add(timeUnit, new ArrayList<>());

            for (Task t : taskList) {
                if (timeUnit % t.getPeriod() == 0) {

                    if (!waitingMap.containsKey(timeUnit + t.getPeriod())) {
                        waitingMap.put(timeUnit + t.getPeriod(), new ArrayList<>());
                    }

                    for (int i = 0; i < t.getET(); i++) {
                        waitingMap.get(timeUnit + t.getPeriod()).add(t);
                    }

                    out.getDeadlinesList().get(timeUnit).add(t);
                }
            }

            if (!waitingMap.isEmpty()) {
                Integer minKey = waitingMap.keySet().stream().min(Integer::compareTo).get();
                Task t = waitingMap.get(minKey).get(0);
                out.getTaskList().add(t);
                waitingMap.get(minKey).remove(0);
                if (waitingMap.get(minKey).isEmpty()) {
                    waitingMap.remove(minKey);
                }
            } else {
                out.getTaskList().add(null);
            }

        }
        System.out.println(out.getTaskList());
        Task prev = null;
        int index = 1;
        boolean flag = false;
        for (Task t : out.getTaskList()) {

            if (prev == null) {
                scheduleMap.put(t.getName(), 1);
                dead_lines.add(t.getPeriod());
            } else if (!scheduleMap.containsKey(t.getName())) {
                scheduleMap.put(t.getName(), 1);
                dead_lines.add(t.getPeriod());
            } else {
                int i = scheduleMap.get(t.getName());
                if (t.getName().equals(prev.getName()) && t.getET() == 1) {
                    scheduleMap.put(t.getName(), i+1);
                    dead_lines.add(t.getPeriod()*(i+1));
                }else if (!t.getName().equals(prev.getName())){
                    scheduleMap.put(t.getName(), i+1);
                    dead_lines.add(t.getPeriod()*(i+1));
                }
            }
            if(index>dead_lines.get(dead_lines.size()-1)){
                ScheduledTasks.message = t.getName()+" missed the deadline!!!";
                flag = true;
                break;
            }
            prev = t;
            index++;
        }
        out.getDeadlinesList().remove(0);
        out.getDeadlinesList().add(new ArrayList<>());
        if(flag){
            ScheduledTasks outList = new ScheduledTasks();
            for(int i=0;i<index;i++){
                outList.getTaskList().add(out.getTaskList().get(i));
            }

            return outList;
        }
        return out;
    }

	
    private void schedule() {

        styleList = new ArrayList<>(Arrays.asList(style));
        int lcm = Integer.parseInt(lblLCM.getText());
        TaskScheduler.ScheduledTasks scheduledTasks = TaskScheduler.schedule(taskList, lcm);
        List<Task> scheduledTaskList = new ArrayList<>(scheduledTasks.getTaskList());
        List<List<Task>> deadlinesList = new ArrayList<>(scheduledTasks.getDeadlinesList());
        //fillTable(scheduledTaskList, deadlinesList);
        TaskSchedulerChart chart = drawChart(scheduledTaskList);
        chartScene.setRoot(chart);
        if (TaskScheduler.ScheduledTasks.message.equals("")) {
            txtStatus.setText("All Tasks Completed Successfully");
            txtStatus.setTextFill(Color.GREEN);

        } else {
            txtStatus.setText(TaskScheduler.ScheduledTasks.message);
            txtStatus.setTextFill(Color.RED);
        }
    }

    
    private void schedulePredefined() {

        styleList = new ArrayList<>(Arrays.asList(style));
        int lcm = Integer.parseInt(lblLCM.getText());
        List<Task> taskListPredefined = new ArrayList<>();
        String[] strs = "".split(",");
        for (String str : strs) {
            for (Task t : taskList) {
                if (t.getName().equals(str)) {
                    taskListPredefined.add(t);
                }
            }
        }
        List<Task> scheduledTaskList = new ArrayList<>(taskListPredefined);
        TaskSchedulerChart chart = drawChart(scheduledTaskList);
        chartScene.setRoot(chart);
        txtStatus.setText("T1 missed the deadline");
            txtStatus.setTextFill(Color.RED);
    }

    private TaskSchedulerChart drawChart(List<Task> scheduledTaskList) {

        List<String> nameList = new ArrayList<>();
        for (Task t : taskList) {
            nameList.add(t.getName());
        }

        for (Task t : taskList) {
            ObservableList<XYChart.Data<Number, String>> seriesData = FXCollections.observableArrayList();
            String styleClass = getRandomStyle();
            for (int i = 0; i < scheduledTaskList.size(); i++) {
                if (t.equals(scheduledTaskList.get(i))) {
                    seriesData.add(new XYChart.Data<>(i, t.getName(), new TaskSchedulerChart.ExtraData(styleClass)));
                }
            }
            chartData.add(new XYChart.Series<>(seriesData));
        }
        chart.setData(chartData);
        chart.getStylesheets().add(getClass().getResource("myapp.css").toExternalForm());

        return chart;
    }

static RMScheduledTasks schedule(final List<Task> taskList, int lcm) {

        ArrayList<Integer> dead_lines = new ArrayList<>();
        HashMap<String, Integer> scheduleMap = new HashMap<>();
        ScheduledTasks out = new ScheduledTasks();
        Map<Integer, List<Task>> waitingMap = new HashMap<>();
	Map<Integer, List<Task>> priorityMap = new HashMap<>();

        for (int timeUnit = 0; timeUnit < lcm; timeUnit++) {
            out.getDeadlinesList().add(timeUnit, new ArrayList<>());

            for (Task t : taskList) {
                if (timeUnit % t.getPeriod() == 0) {

                    if (!priorityMap.containsKey(timeUnit + t.getPeriod())) {
                        waitingMap.put(timeUnit + t.getPeriod(), new ArrayList<>());
                    }

                    for (int i = 0; i < t.getET(); i++) {
                        priorityMap.get(timeUnit + t.getPeriod()).add(t);
                    }

                    out.getDeadlinesList().get(timeUnit).add(t);
                }
            }

        
	}
	if (!priorityMap.isEmpty()) {
                Integer minKey = priorityMap.keySet().stream().min(Integer::compareTo).get();
                Task t = waitingMap.get(minKey).get(0);
                out.getTaskList().add(t);
                priorityMap.get(minKey).remove(0);
                if (priorityMap.get(minKey).isEmpty()) {
                    priorityMap.remove(minKey);
                }

}

}
