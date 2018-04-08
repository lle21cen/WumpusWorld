
/*
 * Class that defines the agent function.
 * 
 * Written by James P. Biagioni (jbiagi1@uic.edu)
 * for CS511 Artificial Intelligence II
 * at The University of Illinois at Chicago
 * 
 * Last modified 2/19/07 
 * 
 * DISCLAIMER:
 * Elements of this application were borrowed from
 * the client-server implementation of the Wumpus
 * World Simulator written by Kruti Mehta at
 * The University of Texas at Arlington.
 * 
 */

import java.util.Random;

class AgentFunction {

	// string to store the agent's name
	// do not remove this variable
	private String agentName = "Agent Smith";

	// all of these variables are created and used
	// for illustration purposes; you may delete them
	// when implementing your own intelligent agent
	private int[] actionTable;
	private int[] location;
	private boolean[][][] percept;
	private int nx[], ny[];
	private int repeat;
	private int[] action;
	private int worldSize;
	private char direction;
	private boolean bump;
	private boolean glitter;
	private boolean breeze;
	private boolean stench;
	private boolean scream;
	private Random rand;

	public AgentFunction(int worldSize, int[] location, char direction) {
		// for illustration purposes; you may delete all code
		// inside this constructor when implementing your
		// own intelligent agent

		// this integer array will store the agent actions
		actionTable = new int[8];
		nx = new int[4];
		ny = new int[4];
		action = new int[3];
		this.worldSize = worldSize;
		percept = new boolean[worldSize][worldSize][2];
		this.location = location;
		this.direction = direction;
		actionTable[0] = Action.GO_FORWARD;
		actionTable[1] = Action.GO_FORWARD;
		actionTable[2] = Action.GO_FORWARD;
		actionTable[3] = Action.GO_FORWARD;
		actionTable[4] = Action.TURN_RIGHT;
		actionTable[5] = Action.TURN_LEFT;
		actionTable[6] = Action.GRAB;
		actionTable[7] = Action.SHOOT;

		// new random number generator, for
		// randomly picking actions to execute
		rand = new Random();
		for (int i = 0; i < worldSize; ++i)
			for (int j = 0; j < worldSize; ++j)
				for (int k = 0; k < 2; ++k)
					percept[i][j][k] = true;
		nx[0] = 1;
		nx[1] = 0;
		nx[2] = -1;
		nx[3] = 0; // N,E,S,W
		ny[0] = 0;
		ny[1] = 1;
		ny[2] = 0;
		ny[3] = -1;
		repeat = 0;
		percept[location[0]][location[1]][0] = false;
		percept[location[0]][location[1]][1] = false;
		
	}

	public int process(TransferPercept tp) {
		// To build your own intelligent agent, replace
		// all code below this comment block. You have
		// access to all percepts through the object
		// 'tp' as illustrated here:

		// read in the current percepts
		bump = tp.getBump();
		glitter = tp.getGlitter();
		breeze = tp.getBreeze();
		stench = tp.getStench();
		scream = tp.getScream();
		int[] goActionTable = new int[3];
		goActionTable[0] = Action.TURN_RIGHT;
		goActionTable[1] = Action.TURN_LEFT;
		goActionTable[2] = Action.GO_FORWARD;

		int i = 0, j = 0;
		int y = location[0];
		int x = location[1];
		int randVal2 = rand.nextInt(2);
		percept[y][x][0] = false;
		percept[y][x][1] = false;
			
		// 2-1]
		if (y == worldSize - 1 && x == worldSize - 1) {
			if (stench || breeze)
				return Action.GO_FORWARD; // 죽는 것보다 낫다. 
			else {
				if (direction == 'E') {
					direction = 'S';
					return Action.TURN_RIGHT;
				}
				else {
					direction = 'W';
					return Action.TURN_LEFT;
				}
			}
		} else if (y == worldSize - 1 && x == 0) {
			if (stench || breeze)
				return Action.GO_FORWARD;
			else {
				if (direction == 'W') {
					direction = 'S';
					return Action.TURN_LEFT;
				}
				else {
					direction = 'E';
					return Action.TURN_RIGHT;
				}
			}
		} else if (y == 0 && x == worldSize - 1) {
			if (stench || breeze)
				return Action.GO_FORWARD;
			else {
				if (direction == 'E') {
					direction = 'N';
					return Action.TURN_LEFT;
				}
				else {
					direction = 'W';
					return Action.TURN_RIGHT;
				}
			}
		} else if (y == 0 && x == 0) {
			if (stench || breeze)
				return Action.GO_FORWARD;
			else {
				if (direction == 'W') {
					direction = 'N';
					return Action.TURN_RIGHT;
				}
				else {
					direction = 'E';
					return Action.TURN_LEFT;
				}
			}
		}

		// 2-2]
		if (bump == true || glitter == true || breeze == true || stench == true || scream == true) {
			// to do
			if (scream) {
				for (i = 0; i < worldSize; i++)
					for (j = 0; j < worldSize; j++)
						if (percept[i][j][1])
							percept[i][j][1] = false;
			}
			// 2-3]
			if (bump) {
				if (direction == 'E') {
					if (percept[y + 1][x][1] || percept[y + 1][x][0]) {
						direction = 'S';
						return Action.TURN_RIGHT;
					}
					else if (percept[y - 1][x][1] || percept[y - 1][x][0]) {
						direction = 'N';
						return Action.TURN_LEFT;
					}
				} else if (direction == 'W') {
					if (percept[y + 1][x][1] || percept[y + 1][x][0]) {
						direction = 'S';
						return Action.TURN_LEFT;
					}
					else if (percept[y - 1][x][1] || percept[y - 1][x][0]) {
						direction = 'N';
						return Action.TURN_RIGHT;
					}
				} else if (direction == 'S') {
					if (percept[y][x + 1][1] || percept[y][x + 1][0]) {
						direction = 'W';
						return Action.TURN_RIGHT;
					}
					else if (percept[y][x - 1][1] || percept[y][x - 1][0]) {
						direction = 'E';
						return Action.TURN_LEFT;
					}
				} else if (direction == 'N') {
					if (percept[y][x + 1][1] || percept[y][x + 1][0]) {
						direction = 'W';
						return Action.TURN_LEFT;
					}
					else if (percept[y][x - 1][1] || percept[y][x - 1][0]) {
						direction = 'E';
						return Action.TURN_RIGHT;
					}
				}
				setDirection(goActionTable[randVal2]);
				return goActionTable[randVal2];
			}
			// 2-4]
			if (glitter)
				return Action.GRAB;
			// 2-5]
			if (stench) {
				int cnt = 0, dir_num = 0;
				
				for (i = 0; i < 4; i++) {
					if (0 <= y + ny[i] && y + ny[i] < worldSize && 0 <= x + nx[i] && x + nx[i] < worldSize) {
						if (!percept[y + ny[i]][x + nx[i]][1]) {
							cnt++;
						} else
							dir_num = i;
					} else
						cnt++;
				}
				if (cnt == 3) {
					if (dir_num == 0) {
						if (direction == 'E')
							return Action.SHOOT;
						else {
							if (direction == 'N') {
								goActionTable[0] = Action.TURN_LEFT;
								goActionTable[1] = Action.GO_FORWARD;
								if (randVal2 == 0) direction = 'W';
							} else if (direction == 'S') {
								goActionTable[0] = Action.TURN_RIGHT;
								goActionTable[1] = Action.GO_FORWARD;
								if (randVal2 == 0) direction = 'W';
							} else if (direction == 'W') {
								int random = rand.nextInt(3);
								if (random == 0) direction = 'N';
								else if (random == 1) direction = 'S';
								else direction = 'W';
								return goActionTable[random];
							}
							return goActionTable[randVal2];
						}
					} else if (dir_num == 1) {
						if (direction == 'N')
							return Action.SHOOT;
						else {
							if (direction == 'E') {
								goActionTable[0] = Action.TURN_RIGHT;
								goActionTable[1] = Action.GO_FORWARD;
								if (randVal2 == 0) direction = 'S';
							} else if (direction == 'S') {
								int random = rand.nextInt(3);
								if (random == 0) direction = 'W';
								else if (random == 1) direction = 'E';
								return goActionTable[random];
							} else if (direction == 'W') {
								goActionTable[0] = Action.TURN_LEFT;
								goActionTable[1] = Action.GO_FORWARD;
								if (randVal2 == 0) direction = 'S';
							}
							return goActionTable[randVal2];
						}
					} else if (dir_num == 2) {
						if (direction == 'W')
							return Action.SHOOT;
						else {
							if (direction == 'N') {
								goActionTable[0] = Action.TURN_RIGHT;
								goActionTable[1] = Action.GO_FORWARD;
								if(randVal2 == 0) direction = 'E';
							} else if (direction == 'S') {
								goActionTable[0] = Action.TURN_LEFT;
								goActionTable[1] = Action.GO_FORWARD;
								if(randVal2 == 0) direction = 'E';
							} else if (direction == 'E') {
								int random = rand.nextInt(3);
								if (random == 0) direction = 'S';
								else if (random == 1) direction = 'N';
								return goActionTable[random];
							}
							return goActionTable[randVal2];
						}
					} else if (dir_num == 3) {
						if (direction == 'S')
							return Action.SHOOT;
						else {
							if (direction == 'N') {
								int random = rand.nextInt(3);
								if (random == 0) direction = 'E';
								else if (random == 1) direction = 'W';
								return goActionTable[random];
							} else if (direction == 'E') {
								goActionTable[0] = Action.TURN_LEFT;
								goActionTable[1] = Action.GO_FORWARD;
								if (randVal2 == 0) direction = 'N';
							} else if (direction == 'W') {
								goActionTable[0] = Action.TURN_RIGHT;
								goActionTable[1] = Action.GO_FORWARD;
								if (randVal2 == 0) direction = 'N';
							}
							return goActionTable[randVal2];
						}
					}
				}
			}
			// 2-6]
			if (breeze || stench) {
				if (direction == 'E') {
					if (x + 1 < worldSize && !percept[y][x+1][0] && !percept[y][x+1][1])
						return Action.GO_FORWARD;
					else if (y + 1 < worldSize && !percept[y + 1][x][0] && !percept[y+1][x][1]) {
						direction = 'N';
						return Action.TURN_LEFT;
					}
					else if (y - 1 >= 0 && !percept[y-1][x][0] && !percept[y-1][x][1]) {
						direction = 'S';
						return Action.TURN_RIGHT;
					}
				} else if (direction == 'W') {
					if (y - 1 >= 0 && !percept[y - 1][x][0] && !percept[y - 1][x][1]) {
						setDirection(Action.TURN_LEFT);
						return Action.TURN_LEFT;
					}
					else if (y + 1 < worldSize && !percept[y+1][x][0] && !percept[y+1][x][1]) {
						setDirection(Action.TURN_RIGHT);
						return Action.TURN_RIGHT;
					}
					else if (x - 1 >= 0 && !percept[y][x - 1][0] && !percept[y][x - 1][1]) {
						return Action.GO_FORWARD;
					}
				} else if (direction == 'S') {
					if (y - 1 >= 0 && !percept[y - 1][x][0] && !percept[y - 1][x][1])
						return Action.GO_FORWARD;
					else if (x + 1 < worldSize && !percept[y][x + 1][0] && !percept[y][x + 1][1]) {
						direction = 'E';
						return Action.TURN_LEFT;
					}
					else if (x - 1 >= 0 && !percept[y][x - 1][0] && !percept[y][x - 1][1]) {
						direction = 'W';
						return Action.TURN_RIGHT;
					}
				} else if (direction == 'N') {
					if (y + 1 < worldSize && !percept[y + 1][x][0] && !percept[y + 1][x][1])
						return Action.GO_FORWARD;
					else if (x - 1 >= 0 && !percept[y][x-1][0] && !percept[y][x-1][1]) {
						direction = 'W';
						return Action.TURN_LEFT;
					}
					else if (x + 1 < worldSize && !percept[y][x + 1][0] && !percept[y][x + 1][1]) {
						direction = 'E';
						return Action.TURN_RIGHT;
					}
				}
				int random=rand.nextInt(3);
				setDirection(goActionTable[random]);
				return goActionTable[random];
			}

		} else { // 2-7]
			for (i = 0; i < 4; i++) {
				if (0 <= y + ny[i] && y + ny[i] < worldSize && 0 <= x + nx[i] && x + nx[i] < worldSize) {
					percept[y + ny[i]][x + nx[i]][1] = false;
					percept[y + ny[i]][x + nx[i]][0] = false;
				}
			}
		}
		// return action to be performed
		int random = rand.nextInt(6);
		if (random == 4 || random == 5) {
			setDirection(actionTable[random]);
		}
		return actionTable[random];
	}
	
	public void setDirection(int act) {
		// 3
		if (direction == 'E') {
			if (act == Action.TURN_RIGHT)
				direction = 'S';
			else if (act == Action.TURN_LEFT)
				direction = 'N';
		} else if (direction == 'W') {
			if (act == Action.TURN_RIGHT)
				direction = 'N';
			else if (act == Action.TURN_LEFT)
				direction = 'S';
		} else if (direction == 'S') {
			if (act == Action.TURN_RIGHT)
				direction = 'W';
			else if (act == Action.TURN_LEFT)
				direction = 'E';
		}else if (direction == 'N') {
			if (act == Action.TURN_RIGHT)
				direction = 'E';
			else if (act == Action.TURN_LEFT)
				direction = 'W';
		}
	}
	// public method to return the agent's name
	// do not remove this method
	public String getAgentName() {
		return agentName;
	}
}
