/*
    Programmable Property Files
    Copyright (C) 2004 Marcus Andersson

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

#ifndef BUNDLE_H
#define BUNDLE_H

#include <string>
#include <map>

#include <tcl.h>

using namespace std;

namespace ppf
{
  
class Bundle
{
 public:
  
  typedef map<string, Bundle*> BundleMap;
  typedef map<string, string> PropertyMap;

  Bundle();

  Bundle* newBundle(string, int, Tcl_interp*);
  Bundle* getBundle(string);

  void newProperty(string, string);
  string getProperty(string);

  virtual void ~Bundle();

 private:
  
  BundleMap bundleMap;
  PropertyMap propertyMap;
  Tcl_interp* interp;

};

}    
#endif