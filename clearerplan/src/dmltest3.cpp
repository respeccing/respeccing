/****************************************************************************
*
*                             dmental links
*    Copyright (c) 28 Feb 2005 AtKaaZ, AtKaaZ at users.sourceforge.net
*    Portions Copyright (c) 1983-2002 Sybase, Inc. All Rights Reserved.
*
*  ========================================================================
*
*    This file contains Original Code and/or Modifications of Original
*    Code as defined in and that are subject to the Sybase Open Watcom
*    Public License version 1.0 (the 'License'). You may not use this file
*    except in compliance with the License. BY USING THIS FILE YOU AGREE TO
*    ALL TERMS AND CONDITIONS OF THE LICENSE. A copy of the License is
*    provided with the Original Code and Modifications, and is also
*    available at www.sybase.com/developer/opensource.
*
*    The Original Code and all software distributed under the License are
*    distributed on an 'AS IS' basis, WITHOUT WARRANTY OF ANY KIND, EITHER
*    EXPRESS OR IMPLIED, AND SYBASE AND ALL CONTRIBUTORS HEREBY DISCLAIM
*    ALL SUCH WARRANTIES, INCLUDING WITHOUT LIMITATION, ANY WARRANTIES OF
*    MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, QUIET ENJOYMENT OR
*    NON-INFRINGEMENT. Please see the License for the specific language
*    governing rights and limitations under the License.
*
*  ========================================================================
*
* Description: testing stuff #3
*
****************************************************************************/


#include <stdlib.h>
#include <stdio.h>

#include "_gcdefs.h"

#include "pnotetrk.h"

#include "recstor.h"

void One(int);

void Two(int i)
{
        if (i>6) {
                ERR(reached a stop)
                return;
        }
        INFO_IF(i>=3,);
        One(++i);
}

void One(int i)
{
        WARN_IF(!i,);
        Two(++i);
}

int main()
{
        InitNotifyTracker();

        TRecordsStorage *DataBase = new TRecordsStorage;
        ERR_IF(!DataBase,
                        return (EXIT_FAILURE));

        delete DataBase;
        One(0);
        ShutDownNotifyTracker();

        return EXIT_SUCCESS; //exit(EXIT_SUCCESS);
}
