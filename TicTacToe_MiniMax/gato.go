package main

import (
	"fmt"
	"os"
	"os/exec"
	"syscall"
)

func cleanScreen() {
	c := exec.Command("clear")
	c.Stdout = os.Stdout
	c.Run()
}

func imprimeTablero(tablero map[uint]string) {
	fmt.Println(tablero[1] + "|" + tablero[2] + "|" + tablero[3])
	fmt.Println("-----")
	fmt.Println(tablero[4] + "|" + tablero[5] + "|" + tablero[6])
	fmt.Println("-----")
	fmt.Println(tablero[7] + "|" + tablero[8] + "|" + tablero[9])
	fmt.Print("\n")
}

func casillasDisponibles() {
	fmt.Println("[i] Estas son las casillas:")
	fmt.Println("1, 2, 3 ")
	fmt.Println("4, 5, 6 ")
	fmt.Println("7, 8, 9 ")
}

func existeEspacio(tablero map[uint]string, posicion uint) bool {
	if posicion > 9 {
		return false
	}
	if tablero[posicion] == "-" {
		return true
	}
	return false
}

func checaEmpate(tablero map[uint]string) bool {
	for _, v := range tablero {
		if v == "-" {
			return false
		}
	}
	return true
}

func checaGana(tablero map[uint]string) bool {
	if tablero[1] == tablero[2] && tablero[1] == tablero[3] && tablero[1] != "-" {
		return true
	}
	if tablero[4] == tablero[5] && tablero[4] == tablero[6] && tablero[4] != "-" {
		return true
	}
	if tablero[7] == tablero[8] && tablero[7] == tablero[9] && tablero[7] != "-" {
		return true
	}
	if tablero[1] == tablero[4] && tablero[1] == tablero[7] && tablero[1] != "-" {
		return true
	}
	if tablero[2] == tablero[5] && tablero[2] == tablero[8] && tablero[2] != "-" {
		return true
	}
	if tablero[3] == tablero[6] && tablero[3] == tablero[9] && tablero[3] != "-" {
		return true
	}
	if tablero[1] == tablero[5] && tablero[1] == tablero[9] && tablero[1] != "-" {
		return true
	}
	if tablero[7] == tablero[5] && tablero[7] == tablero[3] && tablero[7] != "-" {
		return true
	}
	return false
}

func checaGanaFicha(tablero map[uint]string, mark string) bool {
	if tablero[1] == tablero[2] && tablero[1] == tablero[3] && tablero[1] == mark {
		return true
	}
	if tablero[4] == tablero[5] && tablero[4] == tablero[6] && tablero[4] == mark {
		return true
	}
	if tablero[7] == tablero[8] && tablero[7] == tablero[9] && tablero[7] == mark {
		return true
	}
	if tablero[1] == tablero[4] && tablero[1] == tablero[7] && tablero[1] == mark {
		return true
	}
	if tablero[2] == tablero[5] && tablero[2] == tablero[8] && tablero[2] == mark {
		return true
	}
	if tablero[3] == tablero[6] && tablero[3] == tablero[9] && tablero[3] == mark {
		return true
	}
	if tablero[1] == tablero[5] && tablero[1] == tablero[9] && tablero[1] == mark {
		return true
	}
	if tablero[7] == tablero[5] && tablero[7] == tablero[3] && tablero[7] == mark {
		return true
	}
	return false
}

func insertaFicha(tablero map[uint]string, ficha string, posicion uint) {
	if existeEspacio(tablero, posicion) {
		tablero[posicion] = ficha
		imprimeTablero(tablero)
		if checaEmpate(tablero) {
			fmt.Println("[i] Empate")
			syscall.Exit(0)
		}
		if checaGana(tablero) {
			if ficha == "X" {
				fmt.Println("[i] Gana la IA")
				syscall.Exit(0)
			} else {
				fmt.Println("[i] Gana el jugador")
				syscall.Exit(0)
			}
		}
	} else {
		cleanScreen()
		casillasDisponibles()
		imprimeTablero(tablero)
		fmt.Printf("[!] No se puede insertar la ficha en %d\n", posicion)
		fmt.Print("[+] Ingresa un nueva casilla \n-> ")
		fmt.Scanf("%d", &posicion)
		insertaFicha(tablero, ficha, posicion)
	}
}

func movimientoJugador(tablero map[uint]string, ficha string) {
	var posicion uint
	fmt.Print("[+] Ingresa una casilla para tirar 'O'\n-> ")
	fmt.Scanf("%d", &posicion)
	insertaFicha(tablero, "O", posicion)
}

func movimientoComputadora(tablero map[uint]string, ficha string) {
	var bestMove uint
	bestScore := -800
	bestMove = 0
	for key, ficha := range tablero {
		if ficha == "-" {
			tablero[key] = ficha
			score := minimax(tablero, 0, false)
			tablero[key] = "-"
			if score > bestScore {
				bestScore = score
				bestMove = key
			}
		}
	}
	insertaFicha(tablero, ficha, bestMove)
}

func minimax(tablero map[uint]string, depth int, isMaximizing bool) int {
	if checaGanaFicha(tablero, "X") {
		return 1
	}
	if checaGanaFicha(tablero, "O") {
		return -1
	}
	if checaEmpate(tablero) {
		return 0
	}

	if isMaximizing {
		bestScore := -800
		for key, ficha := range tablero {
			if ficha == "-" {
				tablero[key] = "X"
				score := minimax(tablero, depth+1, false)
				tablero[key] = "-"
				if score > bestScore {
					bestScore = score
				}
			}
		}
		return bestScore
	} else {
		bestScore := 800
		for key, ficha := range tablero {
			if ficha == "-" {
				tablero[key] = "O"
				score := minimax(tablero, depth+1, true)
				tablero[key] = "-"
				if score < bestScore {
					bestScore = score
				}

			}
		}
		return bestScore
	}
}

func main() {
	tablero := map[uint]string{1: "-", 2: "-", 3: "-",
		4: "-", 5: "-", 6: "-",
		7: "-", 8: "-", 9: "-"}

	fmt.Println("\t[i] Juego del Gato MiniMax en Golang")
	turno := 0
	for {
		fmt.Print("[+] Eliga \"1\" para ser el primer jugador o \"2\" para ser el segundo\n-> ")
		fmt.Scanf("%d", &turno)
		if turno == 1 || turno == 2 {
			break
		}
		cleanScreen()
	}
	for {
		if checaGana(tablero) {
			break
		}
		fmt.Println("-------------------------------")
		casillasDisponibles()
		if turno == 1 {
			movimientoJugador(tablero, "O")
			movimientoComputadora(tablero, "X")
		} else {
			movimientoComputadora(tablero, "X")
			movimientoJugador(tablero, "O")
		}
	}
}
