; Copyright (c) AtKaaZ and contributors.
; All rights reserved.
; The use and distribution terms for this software are covered by the
; Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
; which can be found in the file epl-v10.txt at the root of this distribution.
; By using this software in any fashion, you are agreeing to be bound by
; the terms of this license.
; You must not remove this notice, or any other, from this software.

(ns runtime.util-test
  (:use [midje.sweet])
  ;(:use runtime.q :reload-all)
  (:use runtime.util :reload-all)
  )

(fact "something" 
      (encast connrandomeseehtihtdahd210euowkjas) 
      => (throws clojure.lang.Compiler$CompilerException)
      ) 
