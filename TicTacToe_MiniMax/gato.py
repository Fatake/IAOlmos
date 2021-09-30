import os


def imprimeTablero(tablero):
    print(tablero[1] + '|' + tablero[2] + '|' + tablero[3])
    print('-----')
    print(tablero[4] + '|' + tablero[5] + '|' + tablero[6])
    print('-----')
    print(tablero[7] + '|' + tablero[8] + '|' + tablero[9])
    print("\n")

def existeEspacio(posicion):
    if posicion > 9:
        return False
    if tablero[posicion] == ' ':
        return True
    else:
        return False

def insertaFicha(ficha, posicion):
    if existeEspacio(posicion):
        tablero[posicion] = ficha
        imprimeTablero(tablero)
        if (checaEmpate()):
            print("[i]Empate")
            exit()
        if checaGana():
            if ficha == 'X':
                print("[i]Gana la IA")
                exit()
            else:
                print("[i]Gana el jugador")
                exit()
        return
    else:
        print("[!] No se puede insertar la ficha en {1}".format(posicion))
        posicion = int(input("[+] Ingresa un nueva casilla \n-> "))
        insertaFicha(ficha, posicion)
        return

def checaGana():
    if (tablero[1] == tablero[2] and tablero[1] == tablero[3] and tablero[1] != ' '):
        return True
    elif (tablero[4] == tablero[5] and tablero[4] == tablero[6] and tablero[4] != ' '):
        return True
    elif (tablero[7] == tablero[8] and tablero[7] == tablero[9] and tablero[7] != ' '):
        return True
    elif (tablero[1] == tablero[4] and tablero[1] == tablero[7] and tablero[1] != ' '):
        return True
    elif (tablero[2] == tablero[5] and tablero[2] == tablero[8] and tablero[2] != ' '):
        return True
    elif (tablero[3] == tablero[6] and tablero[3] == tablero[9] and tablero[3] != ' '):
        return True
    elif (tablero[1] == tablero[5] and tablero[1] == tablero[9] and tablero[1] != ' '):
        return True
    elif (tablero[7] == tablero[5] and tablero[7] == tablero[3] and tablero[7] != ' '):
        return True
    else:
        return False

def checaGanaFicha(mark):
    if tablero[1] == tablero[2] and tablero[1] == tablero[3] and tablero[1] == mark:
        return True
    elif (tablero[4] == tablero[5] and tablero[4] == tablero[6] and tablero[4] == mark):
        return True
    elif (tablero[7] == tablero[8] and tablero[7] == tablero[9] and tablero[7] == mark):
        return True
    elif (tablero[1] == tablero[4] and tablero[1] == tablero[7] and tablero[1] == mark):
        return True
    elif (tablero[2] == tablero[5] and tablero[2] == tablero[8] and tablero[2] == mark):
        return True
    elif (tablero[3] == tablero[6] and tablero[3] == tablero[9] and tablero[3] == mark):
        return True
    elif (tablero[1] == tablero[5] and tablero[1] == tablero[9] and tablero[1] == mark):
        return True
    elif (tablero[7] == tablero[5] and tablero[7] == tablero[3] and tablero[7] == mark):
        return True
    else:
        return False

def checaEmpate():
    for key in tablero.keys():
        if (tablero[key] == ' '):
            return False
    return True

def movimientoJugador():
    posicion = int(input("[+] Ingresa una casilla para tirar 'O'\n-> "))
    insertaFicha(jugadorFicha, posicion)
    return

def movimientoComputadora():
    bestScore = -800
    bestMove = 0
    for key in tablero.keys():
        # checa el peso de cada lugar
        if (tablero[key] == ' '):
            tablero[key] = computadoraIA
            # Optiene un score
            score = minimax(tablero, 0, False)
            tablero[key] = ' '
            # optiene el mejor resultado
            if (score > bestScore):
                bestScore = score
                bestMove = key

    insertaFicha(computadoraIA, bestMove)
    return

def minimax(tablero, depth, isMaximizing):
    if (checaGanaFicha(computadoraIA)):
        return 1
    elif (checaGanaFicha(jugadorFicha)):
        return -1
    elif (checaEmpate()):
        return 0

    if (isMaximizing): # Es maximo
        bestScore = -800
        for key in tablero.keys():
            if (tablero[key] == ' '):
                tablero[key] = computadoraIA
                score = minimax(tablero, depth + 1, False)
                tablero[key] = ' '
                if (score > bestScore):
                    bestScore = score
        return bestScore
    else: # es minimo
        bestScore = 800
        for key in tablero.keys():
            if (tablero[key] == ' '):
                tablero[key] = jugadorFicha
                score = minimax(tablero, depth + 1, True)
                tablero[key] = ' '
                if (score < bestScore):
                    bestScore = score
        return bestScore

def clearScreen():
    if os.name == 'posix':
        _ = os.system('clear')
    else:
        _ = os.system('cls')

def casillasDisponibles():
    print("[i] Estas son las casillas:")
    print("1, 2, 3 ")
    print("4, 5, 6 ")
    print("7, 8, 9 \n")


tablero = {1: ' ', 2: ' ', 3: ' ',
        4: ' ', 5: ' ', 6: ' ',
        7: ' ', 8: ' ', 9: ' '}

imprimeTablero(tablero)

while True:
    clearScreen()
    turno = int(input("[+] Eliga \"1\" para ser el primer jugador o \"2\" para ser el segundo\n-> "))
    if turno == 1 or turno == 2 :
        break

# 
jugadorFicha = 'O'

computadoraIA = 'X'

while not checaGana():
    print("-------------------------------")
    casillasDisponibles()
    if turno == 1:
        movimientoJugador()
        movimientoComputadora()
    else:
        movimientoComputadora()
        movimientoJugador()