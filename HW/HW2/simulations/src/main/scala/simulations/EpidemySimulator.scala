package simulations

import math.random

class EpidemySimulator extends Simulator {

  def randomBelow(i: Int) = (random * i).toInt

  protected[simulations] object SimConfig {
    val population: Int = 300
    val roomRows: Int = 8
    val roomColumns: Int = 8
    val incubationTime = 6
    val dieTime = 14
    val immuneTime = 16
    val healTime = 18
    val prevalenceRate = 0.01
    val transRate = 0.4
    val dieRate = 0.25
    val traffic = 0.5
    val startInfected = (population * 0.01).toInt

    // to complete: additional parameters of simulation
  }

  import SimConfig._

  val persons: List[Person] =
    (1 to population).toList map (id => new Person(id))

  def personsByField = persons.groupBy(person => (person.row, person.col))

  def neighbours(row: Int, col: Int) = {
    val directions = List((1, 0), (-1, 0), (0, 1), (0, -1))
    for {
      direction <- directions
      nRow = (((row + direction._1) % roomRows) + roomRows) % roomRows //to avoid negatives
      nCol = (((col + direction._2) % roomColumns) + roomColumns) % roomColumns
    } yield (nRow, nCol)
  }

  class Person(val id: Int) {
    var infected = (id <= startInfected)
    var sick = false
    var immune = false
    var dead = false

    // demonstrates random number generation
    var row: Int = randomBelow(roomRows)
    var col: Int = randomBelow(roomColumns)

    //
    // to complete with simulation logic
    //
    def moveAction {
      def infect() {
        val personsInRoom = personsByField.get((row, col))
        if (personsInRoom.isDefined) {
          if (personsInRoom.get.exists(p => p.infected)) {
            if (random <= transRate) {
              infected = true
              afterDelay(6)(getSickAction)
            }
          }
        }
      }
      if (!dead) {
        if (random <= traffic) {
          row = randomBelow(roomRows)
          col = randomBelow(roomColumns)
          infect()
        } else {
          val notSickNeighbours = neighbours(row, col).filter(field => {
            val persons = personsByField.get(field)
            !persons.isDefined || !persons.get.exists(p => p.sick || p.dead)
          })
          if (notSickNeighbours.length != 0) {
            val neighbour = notSickNeighbours(randomBelow(notSickNeighbours.length))
            row = neighbour._1
            col = neighbour._2
            infect()
          }
        }
        afterDelay(randomBelow(5) + 1)(moveAction)
      }
    }

    def getSickAction() {
      sick = true
      afterDelay(8)(dieAction)
    }

    def dieAction() {
      if (random <= 0.25) dead = true
      else afterDelay(2)(getImmuneAction)
    }

    def getImmuneAction() {
      if (!dead) {
        sick = false
        immune = true
        afterDelay(2)(getNormalAction)
      }
    }

    def getNormalAction() {
      if (!dead) {
        infected = false
        immune = false
      }
    }

    if (infected) {
      afterDelay(6)(getSickAction)
    }

    afterDelay(randomBelow(5) + 1)(moveAction)
  }
}
