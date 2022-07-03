/*  Enigma - Onion Routing based messaging app.
    Copyright (C) 2022  Romulus-Emanuel Ruja <romulus-emanuel.ruja@tutanota.com>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

package com.example.enigma.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface NodeDao {

    @Query("SELECT * FROM nodes")
    List<Node> getAll();

    @Query("SELECT * FROM nodes WHERE address = :address")
    Node findByAddress(String address);

    @Insert
    void insertAll(Node... nodes);

    @Delete
    void delete(Node node);

    @Transaction
    @Query("SELECT * FROM nodes")
    List<NodeWithNeighbor> getNodesWithNeighbors();

    @Transaction
    @Query("SELECT * FROM nodes WHERE address = :source")
    NodeWithNeighbor getNeighbors(String source);

    @Query("DELETE FROM nodes")
    void clear();

    @Query("SELECT publicKeyPEM FROM nodes WHERE address=:address")
    String getPublicKeyPEM(String address);
}
