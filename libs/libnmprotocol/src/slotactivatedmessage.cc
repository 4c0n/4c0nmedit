/*
    Nord Modular Midi Protocol 3.03 Library
    Copyright (C) 2003 Marcus Andersson

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/

#include "nmprotocol/slotactivatedmessage.h"
#include "nmprotocol/nmprotocollistener.h"
#include "nmprotocol/midiexception.h"
#include "pdl/packet.h"

SlotActivatedMessage::SlotActivatedMessage()
{
  slot = 0;
  sc = 0x09;
  activeSlot = 0;

  expectsreply = true;
}

SlotActivatedMessage::SlotActivatedMessage(Packet* packet)
{
  slot = packet->getVariable("slot");
  activeSlot = packet->getVariable("data:data:slot");
}

SlotActivatedMessage::~SlotActivatedMessage()
{
}

void SlotActivatedMessage::getBitStream(BitStreamList* bitStreamList)
{
  IntStream intStream;
  intStream.append(0x17);
  intStream.append(slot);
  intStream.append(0x41);
  intStream.append(sc);
  intStream.append(activeSlot);
  appendChecksum(&intStream);

  BitStream bitStream;
  MidiMessage::getBitStream(intStream, &bitStream);
  bitStreamList->push_back(bitStream);  
}

void SlotActivatedMessage::notifyListener(NMProtocolListener* listener)
{
  listener->messageReceived(*this);
}

int SlotActivatedMessage::getActiveSlot()
{
  return activeSlot;
}

void SlotActivatedMessage::setActiveSlot(int slot)
{
  activeSlot = slot;
}

