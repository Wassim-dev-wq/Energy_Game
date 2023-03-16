Abstract Class Tile
- connectedEdges: Set<Integer>
- component: Component
+ rotateClockwise(): void
+ rotateCounterClockwise(): void
+ isConnectedTo(Tile): boolean
+ addComponent(Component): void
+ removeComponent(): void
+ getComponent(): Component

Class SquareTile extends Tile

Class HexagonalTile extends Tile

Enum Component
- SOURCE
- LAMP
- WIFI

Class Board
- tiles: Tile[][]
+ loadLevel(String): void
+ checkSolution(): boolean
+ getTileAt(int, int): Tile

Class Game
- board: Board
+ startGame(): void
+ selectLevel(int): void
+ displayMainMenu(): void
+ displayGameScreen(): void
+ displayLevelEditor(): void

Class LevelEditor
- board: Board
+ loadLevel(String): void
+ saveLevel(String): void
+ addComponentToTile(int, int, Component): void
+ removeComponentFromTile(int, int): void
+ addConnectedEdgeToTile(int, int, int): void
+ removeConnectedEdgeFromTile(int, int, int): void
