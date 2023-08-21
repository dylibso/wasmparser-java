(module
  (type (;0;) (func (param i32 i32 i32) (result i32)))
  (type (;1;) (func))
  (type (;2;) (func (result i32)))
  (type (;3;) (func (result i64)))
  (type (;4;) (func (param i32) (result i32)))
  (type (;5;) (func (param i32)))
  (type (;6;) (func (param i32 i32)))
  (func (;0;) (type 1))
  (func (;1;) (type 1)
    block  ;; label = @1
      i32.const 0
      i32.const 1
      br_if 0 (;@1;)
      i32.ctz
      drop
    end)
  (func (;2;) (type 1)
    block  ;; label = @1
      i64.const 0
      i32.const 1
      br_if 0 (;@1;)
      i64.ctz
      drop
    end)
  (func (;3;) (type 2) (result i32)
    block (result i32)  ;; label = @1
      i32.const 1
      i32.const 1
      br_if 0 (;@1;)
      i32.ctz
    end)
  (func (;4;) (type 3) (result i64)
    block (result i64)  ;; label = @1
      i64.const 2
      i32.const 1
      br_if 0 (;@1;)
      i64.ctz
    end)
  (func (;5;) (type 4) (param i32) (result i32)
    block  ;; label = @1
      local.get 0
      br_if 0 (;@1;)
      i32.const 2
      return
    end
    i32.const 3)
  (func (;6;) (type 4) (param i32) (result i32)
    block  ;; label = @1
      call 0
      local.get 0
      br_if 0 (;@1;)
      i32.const 2
      return
    end
    i32.const 3)
  (func (;7;) (type 5) (param i32)
    block  ;; label = @1
      call 0
      call 0
      local.get 0
      br_if 0 (;@1;)
    end)
  (func (;8;) (type 4) (param i32) (result i32)
    block (result i32)  ;; label = @1
      i32.const 10
      local.get 0
      br_if 0 (;@1;)
      drop
      i32.const 11
      return
    end)
  (func (;9;) (type 4) (param i32) (result i32)
    block (result i32)  ;; label = @1
      call 0
      i32.const 20
      local.get 0
      br_if 0 (;@1;)
      drop
      i32.const 21
      return
    end)
  (func (;10;) (type 4) (param i32) (result i32)
    block (result i32)  ;; label = @1
      call 0
      call 0
      i32.const 11
      local.get 0
      br_if 0 (;@1;)
    end)
  (func (;11;) (type 4) (param i32) (result i32)
    block  ;; label = @1
      loop  ;; label = @2
        local.get 0
        br_if 1 (;@1;)
        i32.const 2
        return
      end
    end
    i32.const 3)
  (func (;12;) (type 4) (param i32) (result i32)
    block  ;; label = @1
      loop  ;; label = @2
        call 0
        local.get 0
        br_if 1 (;@1;)
        i32.const 2
        return
      end
    end
    i32.const 4)
  (func (;13;) (type 5) (param i32)
    loop  ;; label = @1
      call 0
      local.get 0
      br_if 1 (;@0;)
    end)
  (func (;14;) (type 2) (result i32)
    block (result i32)  ;; label = @1
      i32.const 1
      i32.const 2
      br_if 0 (;@1;)
      br 0 (;@1;)
    end)
  (func (;15;) (type 1)
    block  ;; label = @1
      i32.const 1
      i32.const 1
      br_if 0 (;@1;)
      br_if 0 (;@1;)
    end)
  (func (;16;) (type 2) (result i32)
    block (result i32)  ;; label = @1
      i32.const 1
      i32.const 2
      br_if 0 (;@1;)
      i32.const 3
      br_if 0 (;@1;)
      drop
      i32.const 4
    end)
  (func (;17;) (type 4) (param i32) (result i32)
    block (result i32)  ;; label = @1
      i32.const 2
      i32.const 1
      local.get 0
      br_if 0 (;@1;)
      br_if 0 (;@1;)
      drop
      i32.const 4
    end)
  (func (;18;) (type 1)
    block  ;; label = @1
      i32.const 1
      i32.const 2
      br_if 0 (;@1;)
      br_table 0 (;@1;) 0 (;@1;) 0 (;@1;)
    end)
  (func (;19;) (type 2) (result i32)
    block (result i32)  ;; label = @1
      i32.const 1
      i32.const 2
      br_if 0 (;@1;)
      i32.const 3
      br_table 0 (;@1;) 0 (;@1;) 0 (;@1;)
      i32.const 4
    end)
  (func (;20;) (type 2) (result i32)
    block (result i32)  ;; label = @1
      i32.const 2
      i32.const 1
      i32.const 3
      br_if 0 (;@1;)
      br_table 0 (;@1;) 0 (;@1;)
      i32.const 4
    end)
  (func (;21;) (type 3) (result i64)
    block (result i64)  ;; label = @1
      i64.const 1
      i32.const 2
      br_if 0 (;@1;)
      return
    end)
  (func (;22;) (type 4) (param i32) (result i32)
    block (result i32)  ;; label = @1
      i32.const 1
      local.get 0
      br_if 0 (;@1;)
      if (result i32)  ;; label = @2
        i32.const 2
      else
        i32.const 3
      end
    end)
  (func (;23;) (type 6) (param i32 i32)
    block  ;; label = @1
      local.get 0
      if  ;; label = @2
        local.get 1
        br_if 1 (;@1;)
      else
        call 0
      end
    end)
  (func (;24;) (type 6) (param i32 i32)
    block  ;; label = @1
      local.get 0
      if  ;; label = @2
        call 0
      else
        local.get 1
        br_if 1 (;@1;)
      end
    end)
  (func (;25;) (type 4) (param i32) (result i32)
    block (result i32)  ;; label = @1
      i32.const 3
      i32.const 10
      br_if 0 (;@1;)
      i32.const 2
      local.get 0
      select
    end)
  (func (;26;) (type 4) (param i32) (result i32)
    block (result i32)  ;; label = @1
      i32.const 1
      i32.const 3
      i32.const 10
      br_if 0 (;@1;)
      local.get 0
      select
    end)
  (func (;27;) (type 2) (result i32)
    block (result i32)  ;; label = @1
      i32.const 1
      i32.const 2
      i32.const 3
      i32.const 10
      br_if 0 (;@1;)
      select
    end)
  (func (;28;) (type 0) (param i32 i32 i32) (result i32)
    i32.const -1)
  (func (;29;) (type 2) (result i32)
    block (result i32)  ;; label = @1
      i32.const 12
      i32.const 1
      br_if 0 (;@1;)
      i32.const 2
      i32.const 3
      call 28
    end)
  (func (;30;) (type 2) (result i32)
    block (result i32)  ;; label = @1
      i32.const 1
      i32.const 13
      i32.const 1
      br_if 0 (;@1;)
      i32.const 3
      call 28
    end)
  (func (;31;) (type 2) (result i32)
    block (result i32)  ;; label = @1
      i32.const 1
      i32.const 2
      i32.const 14
      i32.const 1
      br_if 0 (;@1;)
      call 28
    end)
  (func (;32;) (type 0) (param i32 i32 i32) (result i32)
    local.get 0)
  (func (;33;) (type 2) (result i32)
    block (result i32)  ;; label = @1
      i32.const 4
      i32.const 10
      br_if 0 (;@1;)
      i32.const 1
      i32.const 2
      i32.const 0
      call_indirect (type 0)
    end)
  (func (;34;) (type 2) (result i32)
    block (result i32)  ;; label = @1
      i32.const 1
      i32.const 4
      i32.const 10
      br_if 0 (;@1;)
      i32.const 2
      i32.const 0
      call_indirect (type 0)
    end)
  (func (;35;) (type 2) (result i32)
    block (result i32)  ;; label = @1
      i32.const 1
      i32.const 2
      i32.const 4
      i32.const 10
      br_if 0 (;@1;)
      i32.const 0
      call_indirect (type 0)
    end)
  (func (;36;) (type 2) (result i32)
    block (result i32)  ;; label = @1
      i32.const 1
      i32.const 2
      i32.const 3
      i32.const 4
      i32.const 10
      br_if 0 (;@1;)
      call_indirect (type 0)
    end)
  (func (;37;) (type 4) (param i32) (result i32)
    (local i32)
    block (result i32)  ;; label = @1
      i32.const 17
      local.get 0
      br_if 0 (;@1;)
      local.set 0
      i32.const -1
    end)
  (func (;38;) (type 4) (param i32) (result i32)
    block (result i32)  ;; label = @1
      i32.const 1
      local.get 0
      br_if 0 (;@1;)
      local.tee 0
      i32.const -1
      return
    end)
  (func (;39;) (type 4) (param i32) (result i32)
    block (result i32)  ;; label = @1
      i32.const 1
      local.get 0
      br_if 0 (;@1;)
      global.set 0
      i32.const -1
      return
    end)
  (func (;40;) (type 2) (result i32)
    block (result i32)  ;; label = @1
      i32.const 1
      i32.const 1
      br_if 0 (;@1;)
      i32.load
    end)
  (func (;41;) (type 2) (result i32)
    block (result i32)  ;; label = @1
      i32.const 30
      i32.const 1
      br_if 0 (;@1;)
      i32.load8_s
    end)
  (func (;42;) (type 2) (result i32)
    block (result i32)  ;; label = @1
      i32.const 30
      i32.const 1
      br_if 0 (;@1;)
      i32.const 7
      i32.store
      i32.const -1
    end)
  (func (;43;) (type 2) (result i32)
    block (result i32)  ;; label = @1
      i32.const 2
      i32.const 31
      i32.const 1
      br_if 0 (;@1;)
      i32.store
      i32.const -1
    end)
  (func (;44;) (type 2) (result i32)
    block (result i32)  ;; label = @1
      i32.const 32
      i32.const 1
      br_if 0 (;@1;)
      i32.const 7
      i32.store8
      i32.const -1
    end)
  (func (;45;) (type 2) (result i32)
    block (result i32)  ;; label = @1
      i32.const 2
      i32.const 33
      i32.const 1
      br_if 0 (;@1;)
      i32.store16
      i32.const -1
    end)
  (func (;46;) (type 2) (result i32)
    block (result i32)  ;; label = @1
      i32.const 1
      i32.const 1
      br_if 0 (;@1;)
      i32.const 10
      i32.add
    end)
  (func (;47;) (type 2) (result i32)
    block (result i32)  ;; label = @1
      i32.const 10
      i32.const 1
      i32.const 1
      br_if 0 (;@1;)
      i32.sub
    end)
  (func (;48;) (type 2) (result i32)
    block (result i32)  ;; label = @1
      i32.const 0
      i32.const 1
      br_if 0 (;@1;)
      i32.eqz
    end)
  (func (;49;) (type 2) (result i32)
    block (result i32)  ;; label = @1
      i32.const 1
      i32.const 1
      br_if 0 (;@1;)
      i32.const 10
      i32.le_u
    end)
  (func (;50;) (type 2) (result i32)
    block (result i32)  ;; label = @1
      i32.const 10
      i32.const 1
      i32.const 42
      br_if 0 (;@1;)
      i32.ne
    end)
  (func (;51;) (type 2) (result i32)
    block (result i32)  ;; label = @1
      i32.const 1
      i32.const 1
      br_if 0 (;@1;)
      memory.grow
    end)
  (func (;52;) (type 4) (param i32) (result i32)
    i32.const 1
    block (result i32)  ;; label = @1
      i32.const 2
      drop
      i32.const 4
      block (result i32)  ;; label = @2
        i32.const 8
        local.get 0
        br_if 1 (;@1;)
        drop
        i32.const 16
      end
      i32.add
    end
    i32.add)
  (func (;53;) (type 4) (param i32) (result i32)
    i32.const 1
    block (result i32)  ;; label = @1
      i32.const 2
      drop
      block (result i32)  ;; label = @2
        i32.const 8
        local.get 0
        br_if 1 (;@1;)
        drop
        i32.const 4
      end
      br 0 (;@1;)
      i32.const 16
    end
    i32.add)
  (func (;54;) (type 4) (param i32) (result i32)
    i32.const 1
    block (result i32)  ;; label = @1
      i32.const 2
      drop
      block (result i32)  ;; label = @2
        i32.const 8
        local.get 0
        br_if 1 (;@1;)
        drop
        i32.const 4
      end
      i32.const 1
      br_if 0 (;@1;)
      drop
      i32.const 16
    end
    i32.add)
  (func (;55;) (type 4) (param i32) (result i32)
    i32.const 1
    block (result i32)  ;; label = @1
      i32.const 2
      drop
      i32.const 4
      block (result i32)  ;; label = @2
        i32.const 8
        local.get 0
        br_if 1 (;@1;)
        drop
        i32.const 1
      end
      br_if 0 (;@1;)
      drop
      i32.const 16
    end
    i32.add)
  (func (;56;) (type 4) (param i32) (result i32)
    i32.const 1
    block (result i32)  ;; label = @1
      i32.const 2
      drop
      block (result i32)  ;; label = @2
        i32.const 8
        local.get 0
        br_if 1 (;@1;)
        drop
        i32.const 4
      end
      i32.const 1
      br_table 0 (;@1;)
      i32.const 16
    end
    i32.add)
  (func (;57;) (type 4) (param i32) (result i32)
    i32.const 1
    block (result i32)  ;; label = @1
      i32.const 2
      drop
      i32.const 4
      block (result i32)  ;; label = @2
        i32.const 8
        local.get 0
        br_if 1 (;@1;)
        drop
        i32.const 1
      end
      br_table 0 (;@1;)
      i32.const 16
    end
    i32.add)
  (table (;0;) 1 1 funcref)
  (memory (;0;) 1)
  (global (;0;) (mut i32) (i32.const 10))
  (export "type-i32" (func 1))
  (export "type-i64" (func 2))
  (export "type-i32-value" (func 3))
  (export "type-i64-value" (func 4))
  (export "as-block-first" (func 5))
  (export "as-block-mid" (func 6))
  (export "as-block-last" (func 7))
  (export "as-block-first-value" (func 8))
  (export "as-block-mid-value" (func 9))
  (export "as-block-last-value" (func 10))
  (export "as-loop-first" (func 11))
  (export "as-loop-mid" (func 12))
  (export "as-loop-last" (func 13))
  (export "as-br-value" (func 14))
  (export "as-br_if-cond" (func 15))
  (export "as-br_if-value" (func 16))
  (export "as-br_if-value-cond" (func 17))
  (export "as-br_table-index" (func 18))
  (export "as-br_table-value" (func 19))
  (export "as-br_table-value-index" (func 20))
  (export "as-return-value" (func 21))
  (export "as-if-cond" (func 22))
  (export "as-if-then" (func 23))
  (export "as-if-else" (func 24))
  (export "as-select-first" (func 25))
  (export "as-select-second" (func 26))
  (export "as-select-cond" (func 27))
  (export "as-call-first" (func 29))
  (export "as-call-mid" (func 30))
  (export "as-call-last" (func 31))
  (export "as-call_indirect-func" (func 33))
  (export "as-call_indirect-first" (func 34))
  (export "as-call_indirect-mid" (func 35))
  (export "as-call_indirect-last" (func 36))
  (export "as-local.set-value" (func 37))
  (export "as-local.tee-value" (func 38))
  (export "as-global.set-value" (func 39))
  (export "as-load-address" (func 40))
  (export "as-loadN-address" (func 41))
  (export "as-store-address" (func 42))
  (export "as-store-value" (func 43))
  (export "as-storeN-address" (func 44))
  (export "as-storeN-value" (func 45))
  (export "as-binary-left" (func 46))
  (export "as-binary-right" (func 47))
  (export "as-test-operand" (func 48))
  (export "as-compare-left" (func 49))
  (export "as-compare-right" (func 50))
  (export "as-memory.grow-size" (func 51))
  (export "nested-block-value" (func 52))
  (export "nested-br-value" (func 53))
  (export "nested-br_if-value" (func 54))
  (export "nested-br_if-value-cond" (func 55))
  (export "nested-br_table-value" (func 56))
  (export "nested-br_table-value-index" (func 57))
  (elem (;0;) (i32.const 0) func 32))
