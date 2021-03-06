/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chessjava;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author the0ldknighte
 */
public class AI 
{
    private Side side;
        
    private ChessBoard tempBoard;    
    
    public static List<Move> populateMoves(Side side, ChessBoard board)
    {
        List<Move> tempList = new ArrayList<Move>();
        List<Move> whiteMoves = new ArrayList<Move>();
        List<Move> blackMoves = new ArrayList<Move>();        
        List<Move> bestOptions = new ArrayList<Move>();
        
        for(int x = 0; x > 8; x++)
        {
            for(int y = 0; y > 8; y++)
            {
                Point temp = new Point(x,y);
                if(board.getBoardTile(temp).getChessPiece().getPieceType() != PieceType.EMPTY)
                {
                    if(board.getBoardTile(temp).getChessPiece().getSide() == Side.WHITE)
                    {                        
                        tempList = MoveLogic.determineMoves(board.getBoardTile(temp), board);
                        for(int i = 0; i < tempList.size(); i ++)
                        {
                            whiteMoves.add(tempList.get(i));
                        }                        
                    }
                    else if(board.getBoardTile(temp).getChessPiece().getSide() == Side.BLACK)
                    {
                        tempList = MoveLogic.determineMoves(board.getBoardTile(temp), board);
                        for(int i = 0; i < tempList.size(); i ++)
                        {
                            blackMoves.add(tempList.get(i));
                        }                        
                    }
                }
            }
        }
        if(side == Side.WHITE)
        {
            return whiteMoves;
        }
        else if (side == Side.BLACK)
        {
            return blackMoves;
        }
        return tempList;
    }
    
    private List<Move> bestMove(ChessBoard board, List<Move> list)
    {
        List<Move> tempList = list;
        List<Move> bestOptions = new ArrayList<Move>();
        
        for(int i = 0; i < tempList.size(); i++)
        {
            if(tempList.get(i).getTakenPiece().pieceType == PieceType.QUEEN)
            {
                tempList.get(i).setWeight(100);
                bestOptions.add(tempList.get(i));
            }
            else if(tempList.get(i).getTakenPiece().pieceType == PieceType.ROOK)
            {
                tempList.get(i).setWeight(10);
                bestOptions.add(tempList.get(i));
            }
            else if(tempList.get(i).getTakenPiece().pieceType == PieceType.KNIGHT)
            {
                tempList.get(i).setWeight(16);
                bestOptions.add(tempList.get(i));
            }
            else if(tempList.get(i).getTakenPiece().pieceType == PieceType.BISHOP)
            {
                tempList.get(i).setWeight(14);
                bestOptions.add(tempList.get(i));
            }
            else if(tempList.get(i).getTakenPiece().pieceType == PieceType.PAWN)
            {
                tempList.get(i).setWeight(5);
                bestOptions.add(tempList.get(i));
            }
        }
        return bestOptions;
    }
    /*
     *
     * 
     */
    public Move actualMove(Side side, ChessBoard board)
    {
        List<Move> tempList = populateMoves(side, board);
        //this will break the game
        tempList = bestMove(board,tempList);
        Move send = tempList.get(0);
       
        if(tempList.size() > 0)
        {
            send = tempList.get(0);
            for(int i = 1; i < tempList.size();i++)
            {
                    if(tempList.get(i).getWeight() > send.getWeight())
                    {
                        send = tempList.get(i);
                    }
            }
        }
        if(send.getWeight() == 0)
        {
            Random rand = new Random();            
            send = tempList.get(rand.nextInt(tempList.size()));
        }
        return send;
    }
}
