package testgrails
/**
 * Created by ramsharan on 2/5/17.
 */
class DataGenerator {
    static List<Agent> getAgents(int numberOfBank, int numberOfBranch) {
        List<Agent> agents = new ArrayList<>()
        for (int i = 0; i < numberOfBank; i++) {
            String bank = "bank" + i
            for (int j = 0; j < numberOfBranch; j++) {
                String branch = "branch" + j
                Agent agent = new Agent()
                agent.bankName = bank
                agent.branchName = branch
                agents.add(agent)
            }
        }
        return agents
    }
}
